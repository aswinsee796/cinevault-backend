@CrossOrigin("*")
@RestController
@RequestMapping("/Movies")
public class Moviecontroller {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    private Movieservice movser;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getmov")
    public List<Movies> getDetails() {
        return movser.getAllDetails();
    }

    @PostMapping("/{username}/addmov")
    public ResponseEntity<?> postDetails(@PathVariable String username, @RequestBody Movies m) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        m.setUser(userOpt.get());
        Movies savedMovie = movser.SaveDetails(m);
        return ResponseEntity.ok(savedMovie);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file,
                                              @RequestParam("username") String username) {
        try {
            Path userDir = Paths.get(UPLOAD_DIR, username);
            Files.createDirectories(userDir);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = userDir.resolve(filename);
            Files.write(filePath, file.getBytes());

            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/Movies/uploads/")
                    .path(username + "/")
                    .path(filename)
                    .toUriString();

            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping("/uploads/{username}/{filename:.+}")
    public ResponseEntity<byte[]> serveImage(@PathVariable String username, @PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR, username, filename);
            byte[] imageBytes = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                    .header("Content-Type", Files.probeContentType(filePath))
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        movser.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Movies update(@PathVariable long id, @RequestBody Movies m) {
        return movser.update(id, m);
    }

    @GetMapping("/getmov/{id}")
    public Optional<Movies> getById(@PathVariable long id) {
        return movser.getById(id);
    }

    @GetMapping("/user/{username}")
    public List<Movies> getMoviesByUsername(@PathVariable String username) {
        return movser.getMoviesByUsername(username);
    }
}

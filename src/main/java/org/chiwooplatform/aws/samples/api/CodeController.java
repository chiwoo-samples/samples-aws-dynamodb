package org.chiwooplatform.aws.samples.api;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.chiwooplatform.aws.samples.model.CodeMapping;
import org.chiwooplatform.aws.samples.repository.dynamodb.CodeMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StopWatch;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CodeController {

    protected final transient Logger logger = LoggerFactory.getLogger(CodeController.class);

    static final String BASE_URI = "/codes";

    @Autowired
    private CodeMappingRepository repository;

    @PostMapping(value = CodeController.BASE_URI, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@RequestBody CodeMapping model, UriComponentsBuilder uriBuilder) throws Exception {
        URI location = uriBuilder.path(CodeController.BASE_URI + "/{id}").buildAndExpand(model.getId()).toUri();
        CodeMapping result = repository.save(model);
        logger.info("result: {}", result);
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = CodeController.BASE_URI + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable(value = "id", required = true) String id) throws Exception {
        Optional<CodeMapping> result = repository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping(value = CodeController.BASE_URI + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> modify(@PathVariable(value = "id", required = true) String id, @Validated @RequestBody CodeMapping model)
            throws Exception {
        if (model == null || !Objects.equals(id, model.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header(HttpHeaders.WARNING, "Not matched between id and id of model.").build();
        }
        Optional<CodeMapping> result = repository.findById(id);
        if (!result.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.save(model);
        return ResponseEntity.ok(result.get());
    }

    @DeleteMapping(value = CodeController.BASE_URI + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> remove(@PathVariable(value = "id", required = true) String id) throws Exception {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value = CodeController.BASE_URI + "/batch-import", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<String> batchImport(@RequestBody final List<String> files) throws Exception {
        logger.info("files: {}", files);
        final DeferredResult<String> result = new DeferredResult<>();
        importFiles(files, result);
        return result;
    }

    private void importFromFile(final File file) throws IOException {
        if (file == null || !file.exists()) {
            return;
        }
        try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
            stream.map(v -> {
                try {
                    return objectMapper.readValue(v, CodeMapping.class);
                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                    return null;
                }
            }).filter(p -> p != null).forEach(repository::save);
        }
    }

    @Async
    public void importFiles(final List<String> fileNames, final DeferredResult<String> result) throws Exception {
        StopWatch stopWatch = new StopWatch("DynamoDBApplication");
        for (final String fileName : fileNames) {
            File file = new File("./" + fileName);
            stopWatch.start(file.getName());
            importFromFile(file);
            stopWatch.stop();
            logger.info("{}: {}", file.getName(), stopWatch.prettyPrint());
        }
        result.setResult("OK");
    }

}

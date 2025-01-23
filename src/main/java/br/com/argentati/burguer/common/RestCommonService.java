package br.com.argentati.burguer.common;


import br.com.argentati.burguer.exception.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.List;

public class RestCommonService {

    // Método para listas não paginadas
    protected <T> ResponseEntity<List<T>> buildDefaultResponseForList(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(entities);
    }

    // Método para listas paginadas (Page)
    protected <T> ResponseEntity<Page<T>> buildDefaultResponseForPage(Page<T> page) {
        if (page == null || page.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(page);
    }

    protected <T> ResponseEntity<T> buildResponseForPost(T entity, Object... ids) {
        if (entity == null || ids == null || ids.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Construir a URI com múltiplos IDs (PK composta ou simples)
        ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();

        for (Object id : ids) {
            uriBuilder.path("/{id}");
        }

        URI location = uriBuilder.buildAndExpand(ids).toUri();

        // Definir cabeçalhos
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        // Retornar a entidade e os cabeçalhos, com status HTTP 201 Created
        return new ResponseEntity<>(entity, headers, HttpStatus.CREATED);
    }

    // Método para construir uma resposta com uma entidade única
    protected <T> ResponseEntity<T> buildResponseForEntity(T entity) throws RecordNotFoundException {
        if (entity == null) {
            throw new RecordNotFoundException("Entidade não encontrada.");
        }
        return ResponseEntity.ok(entity);
    }

    protected <T> ResponseEntity<T> buildResponseForDelete(T entity) throws RecordNotFoundException {
        return this.buildResponseForEntity(entity);
    }

    // Método para construir uma resposta para um arquivo
    protected ResponseEntity<byte[]> buildResponseForFile(byte[] fileData, String fileName, MediaType mediaType, boolean asAttachment) {
        if (fileData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            HttpHeaders headers = new HttpHeaders();
            if (asAttachment) {
                headers.setContentDispositionFormData("attachment", fileName);
            }
            headers.setContentType(mediaType);
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        }
    }

    // Método para criar resposta para imagens em Base64
    protected ResponseEntity<String> buildResponseForImage(byte[] imageData, String chave) {
        if (imageData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            return ResponseEntity.ok().header("Inline", "filename=\"" + chave + "\"").body(base64Image);
        }
    }

}
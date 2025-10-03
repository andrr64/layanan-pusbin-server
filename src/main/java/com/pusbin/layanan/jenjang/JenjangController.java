package com.pusbin.layanan.jenjang;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.common.dto.ApiResponse;
import com.pusbin.common.dto.GetFilter;
import com.pusbin.layanan.jenjang.dto.RequestTambahJenjang;
import com.pusbin.layanan.jenjang.dto.RequestUpdateJenjang;

@RestController
@RequestMapping("api/v1/jenjang")
public class JenjangController {
    private final JenjangService service;

    public JenjangController(JenjangService service) {
        this.service = service;
    }

    // GET /jenjang
    @GetMapping
    public List<Jenjang> getAll() {
        return service.getAllJenjang();
    }

    // GET /jenjang/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Jenjang> getById(@PathVariable Long id) {
        return service.getJenjangById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /jenjang
    @PostMapping
    public Jenjang create(@RequestBody RequestTambahJenjang body) {
        return service.createJenjang(body.getJenjang());
    }

    // PUT /jenjang/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Jenjang> update(@PathVariable Long id, @RequestBody RequestUpdateJenjang body) {
      try {
            Jenjang updated = service.updateJenjang(id, body.getJenjang());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /jenjang/{id}
    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            service.deleteJenjang(id);
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/filters")
    public ApiResponse<GetFilter> getFilter() {
        try {
            return ApiResponse.success("OK", service.getFilters());
        } catch (Exception e) {
            return ApiResponse.fail(e.toString());
        }
    }

}

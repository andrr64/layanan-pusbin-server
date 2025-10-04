package com.pusbin.layanan.internal.services.jenis_instansi;

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

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.JenisInstansi;
import com.pusbin.layanan.internal.services.jenis_instansi.dto.RequestTambahJenisInstansi;
import com.pusbin.layanan.internal.services.jenis_instansi.dto.RequestUpdateJenisInstansi;

@RestController
@RequestMapping("/api/v1/jenis_instansi")
public class JenisInstansiController {

    private final JenisInstansiService service;

    public JenisInstansiController(JenisInstansiService service) {
        this.service = service;
    }

    // GET semua data
    @GetMapping
    public List<JenisInstansi> getAll() {
        return service.getAllJenisInstansi();
    }

    // GET by id
    @GetMapping("/{id_jenis_instansi}")
    public ResponseEntity<JenisInstansi> getById(@PathVariable Long id_jenis_instansi) {
        return service.getJenisInstansiById(id_jenis_instansi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create
    @PostMapping
    public JenisInstansi create(@RequestBody RequestTambahJenisInstansi body) {
        return service.createJenisInstansi(body.getJenisInstansi());
    }

    // PUT update
    @PutMapping("/{id_jenis_instansi}")
    public ResponseEntity<JenisInstansi> update(
            @PathVariable Long id_jenis_instansi, @RequestBody RequestUpdateJenisInstansi body) {
        try {
            JenisInstansi updated = service.updateJenisInstansi(id_jenis_instansi, body.getJenisInstansi());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id_jenis_instansi}")
    public ResponseEntity<Void> delete(@PathVariable Long id_jenis_instansi) {
        service.deleteJenisInstansi(id_jenis_instansi);
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

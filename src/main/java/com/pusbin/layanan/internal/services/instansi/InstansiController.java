package com.pusbin.layanan.internal.services.instansi;

import java.util.List;

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
import com.pusbin.layanan.internal.models.Instansi;
import com.pusbin.layanan.internal.services.instansi.dto.RequestTambahInstansi;
import com.pusbin.layanan.internal.services.instansi.dto.RequestUpdateInstansi;
import com.pusbin.layanan.internal.services.instansi.dto.ResponseGetTotalInstansi;

@RestController
@RequestMapping("/api/v1/instansi")
public class InstansiController {

    private final InstansiService service;

    public InstansiController(InstansiService service) {
        this.service = service;
    }

    // GET /instansi
    @GetMapping
    public ApiResponse<List<Instansi>> getAll() {
        return ApiResponse.success(
            "Data instansi berhasil ditemukan",
            service.getAllInstansi()
        );
    }

    // GET /instansi/{id}
    @GetMapping("/{id_instansi}")
    public ApiResponse<Instansi> getById(@PathVariable Long id_instansi) {
        return service.getInstansiById(id_instansi)
                .map(instansi -> ApiResponse.success("Instansi berhasil ditemukan", instansi))
                .orElse(ApiResponse.fail("Instansi dengan ID " + id_instansi + " tidak ditemukan"));
    }

    // POST /instansi
    @PostMapping
    public ApiResponse<Instansi> create(@RequestBody RequestTambahInstansi body) {
        Instansi created = service.createInstansi(
                body.getNama_instansi(),
                body.getId_kategori_instansi(),
                body.getId_jenis_instansi(),
                body.getId_wilayah_kerja(),
                body.getId_pokja()
        );
        return ApiResponse.success("Instansi berhasil ditambahkan", created);
    }

    // PUT /instansi/{id}
    @PutMapping("/{id_instansi}")
    public ApiResponse<Instansi> update(@PathVariable Long id_instansi, @RequestBody RequestUpdateInstansi body) {
        try {
            Instansi updated = service.updateInstansi(
                    id_instansi,
                    body.getNama_instansi(),
                    body.getId_kategori_instansi(),
                    body.getId_jenis_instansi(),
                    body.getId_wilayah_kerja(),
                    body.getId_pokja()
            );
            return ApiResponse.success("Instansi berhasil diperbarui", updated);
        } catch (RuntimeException e) {
            return ApiResponse.fail("Instansi dengan ID " + id_instansi + " tidak ditemukan");
        }
    }

    // DELETE /instansi/{id}
    @DeleteMapping("/{id_instansi}")
    public ApiResponse<Void> delete(@PathVariable Long id_instansi) {
        try {
            service.deleteInstansi(id_instansi);
            return ApiResponse.success("Instansi berhasil dihapus", null);
        } catch (RuntimeException e) {
            return ApiResponse.fail("Instansi dengan ID " + id_instansi + " tidak ditemukan");
        }
    }

    @GetMapping("/total-instansi")
    public ApiResponse<ResponseGetTotalInstansi> getTotalInstansi() {
        return ApiResponse.success(
            "Total instansi berhasil ditemukan",
            service.getTotalInstansi()
        );
    }

    @GetMapping("/filters")
    public ApiResponse<GetFilter> getFilter() {
     try {
         return ApiResponse.success("OK", service.getFilter());
     } catch (Exception e) {
         return ApiResponse.fail(e.getMessage()); 
    }
    }
}
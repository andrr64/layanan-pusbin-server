package com.pusbin.layanan.jabatan;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.jabatan.dto.RequestTambahJabatan;
import com.pusbin.layanan.jabatan.dto.RequestUpdateJabatan;

@RestController
@RequestMapping("/api/v1/jabatan")
public class JabatanController {

    private final JabatanService service;

    public JabatanController(JabatanService service) {
        this.service = service;
    }

    @GetMapping
    public List<RequestTambahJabatan> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RequestTambahJabatan getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public RequestTambahJabatan create(@RequestBody RequestTambahJabatan request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public RequestTambahJabatan update(@PathVariable Long id, @RequestBody RequestUpdateJabatan request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

   
}

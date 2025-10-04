-- ======================================================
-- Flyway Migration: V1__create_pusbin_schema.sql
-- Schema: Pusbin Database + Indexing
-- ======================================================

-- ========================
-- MASTER TABLES
-- ========================
CREATE TABLE kategori_instansi (
    id_kategori_instansi BIGSERIAL PRIMARY KEY,
    kategori_instansi VARCHAR(100) NOT NULL
);
CREATE INDEX idx_kategori_instansi_name ON kategori_instansi(kategori_instansi);

CREATE TABLE jenis_instansi (
    id_jenis_instansi BIGSERIAL PRIMARY KEY,
    jenis_instansi VARCHAR(100) NOT NULL
);
CREATE INDEX idx_jenis_instansi_name ON jenis_instansi(jenis_instansi);

CREATE TABLE wilayah_kerja (
    id_wilayah BIGSERIAL PRIMARY KEY,
    nama_wilayah VARCHAR(100) NOT NULL
);
CREATE INDEX idx_wilayah_kerja_name ON wilayah_kerja(nama_wilayah);

CREATE TABLE pokja (
    id_pokja BIGSERIAL PRIMARY KEY,
    nama_pokja VARCHAR(100) NOT NULL
);
CREATE INDEX idx_pokja_name ON pokja(nama_pokja);

CREATE TABLE nomenklatur (
    id_nomenklatur BIGSERIAL PRIMARY KEY,
    nama_nomenklatur VARCHAR(100) NOT NULL
);
CREATE INDEX idx_nomenklatur_name ON nomenklatur(nama_nomenklatur);

CREATE TABLE jenjang (
    id_jenjang BIGSERIAL PRIMARY KEY,
    jenjang VARCHAR(100) NOT NULL
);
CREATE INDEX idx_jenjang_name ON jenjang(jenjang);

CREATE TABLE asn (
    id_asn BIGSERIAL PRIMARY KEY,
    jenis_asn VARCHAR(100) NOT NULL
);
CREATE INDEX idx_asn_jenis ON asn(jenis_asn);

-- ========================
-- RELATIONAL TABLES
-- ========================
CREATE TABLE nama_jabatan (
    id_nama_jabatan BIGSERIAL PRIMARY KEY,
    nama_jabatan VARCHAR(100) NOT NULL,
    id_nomenklatur BIGINT NOT NULL REFERENCES nomenklatur(id_nomenklatur) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE INDEX idx_nama_jabatan_name ON nama_jabatan(nama_jabatan);
CREATE INDEX idx_nama_jabatan_nomenklatur ON nama_jabatan(id_nomenklatur);

CREATE TABLE jabatan (
    id_jabatan BIGSERIAL PRIMARY KEY,
    id_nama_jabatan BIGINT NOT NULL REFERENCES nama_jabatan(id_nama_jabatan) ON UPDATE CASCADE ON DELETE RESTRICT,
    id_jenjang BIGINT NOT NULL REFERENCES jenjang(id_jenjang) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE INDEX idx_jabatan_nama_jabatan ON jabatan(id_nama_jabatan);
CREATE INDEX idx_jabatan_jenjang ON jabatan(id_jenjang);

CREATE TABLE instansi (
    id_instansi BIGSERIAL PRIMARY KEY,
    nama_instansi VARCHAR(150) NOT NULL,
    id_kategori_instansi BIGINT NOT NULL REFERENCES kategori_instansi(id_kategori_instansi) ON UPDATE CASCADE ON DELETE RESTRICT,
    id_jenis_instansi BIGINT NOT NULL REFERENCES jenis_instansi(id_jenis_instansi) ON UPDATE CASCADE ON DELETE RESTRICT,
    id_wilayah_kerja BIGINT NOT NULL REFERENCES wilayah_kerja(id_wilayah) ON UPDATE CASCADE ON DELETE RESTRICT,
    id_pokja BIGINT NOT NULL REFERENCES pokja(id_pokja) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE INDEX idx_instansi_nama ON instansi(nama_instansi);
CREATE INDEX idx_instansi_kategori ON instansi(id_kategori_instansi);
CREATE INDEX idx_instansi_jenis ON instansi(id_jenis_instansi);
CREATE INDEX idx_instansi_wilayah ON instansi(id_wilayah_kerja);
CREATE INDEX idx_instansi_pokja ON instansi(id_pokja);

CREATE TABLE data_agregat (
    id BIGSERIAL PRIMARY KEY,
    id_instansi BIGINT NOT NULL REFERENCES instansi(id_instansi) ON UPDATE CASCADE ON DELETE CASCADE,
    id_jabatan BIGINT NOT NULL REFERENCES jabatan(id_jabatan) ON UPDATE CASCADE ON DELETE CASCADE,
    id_jenis_asn BIGINT NOT NULL REFERENCES asn(id_asn) ON UPDATE CASCADE ON DELETE CASCADE,
    jumlah BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX idx_data_agregat_instansi ON data_agregat(id_instansi);
CREATE INDEX idx_data_agregat_jabatan ON data_agregat(id_jabatan);
CREATE INDEX idx_data_agregat_asn ON data_agregat(id_jenis_asn);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(64) UNIQUE NOT NULL,
    nama VARCHAR(128) NOT NULL,
    username VARCHAR(64) UNIQUE NOT NULL,
    password VARCHAR(64) NOT NULL
);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);

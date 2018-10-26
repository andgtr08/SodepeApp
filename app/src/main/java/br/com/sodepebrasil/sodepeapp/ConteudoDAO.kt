package br.com.sodepebrasil.sodepeapp

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ConteudoDAO {
    @Query("SELECT * FROM conteudo where id = :id")
    fun getById(id: Long) : Conteudo?

    @Query("SELECT * FROM conteudo")
    fun findAll(): List<Conteudo>

    @Insert
    fun insert(conteudo: Conteudo)

    @Delete
    fun delete(conteudo: Conteudo)
}
package br.com.sodepebrasil.sodepeapp

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

// Classe que gerencia o banco de dados.
@Entity(tableName = "conteudo")
class Conteudo : Serializable {

    // Itens do banco de dados
    @PrimaryKey
    var id:Long = 0
    var nome = ""
    var ementa = ""
    var foto = ""
    var professor = ""

    override fun toString(): String {
        return "Conteudo(nome='$nome')"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }
}
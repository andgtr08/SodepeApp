package br.com.sodepebrasil.sodepeapp

import android.arch.persistence.room.Room

object DatabaseManager {

    // Classe que cria a instância do banco de dados.
    private var dbInstance: LMSDatabase
    init {
        val appContext = LMSApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
                appContext, // contexto global
                LMSDatabase::class.java, // Referência da classe do banco
                "lms.sqlite" // nome do arquivo do banco
        ).build()
    }

    // Metodo que retorna a instância
    fun getConteudoDAO(): ConteudoDAO {
        return dbInstance.conteudoDAO()
    }
}
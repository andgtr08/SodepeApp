package br.com.sodepebrasil.sodepeapp

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

// anotação define a lista de entidades e a versão do banco
@Database(entities = arrayOf(Conteudo::class), version = 1)
abstract class LMSDatabase: RoomDatabase() {
    abstract fun conteudoDAO(): ConteudoDAO
}
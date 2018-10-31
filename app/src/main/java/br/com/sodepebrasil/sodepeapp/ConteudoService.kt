package br.com.sodepebrasil.sodepeapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ConteudoService {

    //Endereço do servidor WS
    val host = "http://andgtr08.pythonanywhere.com"
    val TAG = "WS_LMSApp"

    // Metodo que carrega o conteudo, baseando-se na conexão com a internet.
    fun getConteudos (context: Context): List<Conteudo> {
        var conteudos = ArrayList<Conteudo>()
        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/conteudos"
            val json = HttpHelper.get(url)
            conteudos = parserJson(json)
            // salvar offline
            for (d in conteudos) {
                saveOffline(d)
            }
            return conteudos
        } else {
            val dao = DatabaseManager.getConteudoDAO()
            val conteudos = dao.findAll()
            return conteudos
        }
    }

    // Metodo que carrega o conteudo, baseando-se na conexão com a internet, com a entrada do ID do conteudo
    fun getConteudo (context: Context, id: Long): Conteudo? {
        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/conteudos/${id}"
            val json = HttpHelper.get(url)
            val conteudo = parserJson<Conteudo>(json)
            return conteudo
        } else {
            val dao = DatabaseManager.getConteudoDAO()
            val conteudo = dao.getById(id)
            return conteudo
        }
    }

    // Metodo de salvar o conteudo, baseando-se na conexão com a internet.
    fun save(conteudo: Conteudo): Response {
        if (AndroidUtils.isInternetDisponivel(LMSApplication.getInstance().applicationContext)) {
            val json = HttpHelper.post("$host/conteudos", conteudo.toJson())
            return parserJson(json)
        }else {
            val dao = DatabaseManager.getConteudoDAO()
            saveOffline(conteudo)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }
    }

    // Metodo para salvar o conteudo na base local
    fun saveOffline(conteudo: Conteudo) : Boolean {
        val dao = DatabaseManager.getConteudoDAO()
        if (! existeConteudo(conteudo)) {
            dao.insert(conteudo)
        }
        return true

    }

    // Metodo que verifica se o conteudo existe no banco.
    fun existeConteudo(conteudo: Conteudo): Boolean {
        val dao = DatabaseManager.getConteudoDAO()
        return dao.getById(conteudo.id) != null
    }

    // Metodo que deleta o registro no banco, baseando-se na conexão com a internet.
    fun delete(conteudo: Conteudo): Response {
        if (AndroidUtils.isInternetDisponivel(LMSApplication.getInstance().applicationContext)) {
            val url = "$host/conteudos/${conteudo.id}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        } else {
            val dao = DatabaseManager.getConteudoDAO()
            dao.delete(conteudo)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }

    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}
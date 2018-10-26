package br.com.sodepebrasil.sodepeapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro_conteudo.*

class ConteudoCadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_conteudo)
        setTitle("Novo Conteudo")

        salvarConteudo.setOnClickListener {
            val conteudo = Conteudo()
            conteudo.nome = nomeConteudo.text.toString()
            conteudo.ementa = ementaConteudo.text.toString()
            conteudo.professor = professorConteudo.text.toString()
            conteudo.foto = urlFoto.text.toString()
            taskAtualizar(conteudo)
        }
    }

    private fun taskAtualizar(conteudo: Conteudo) {
        // Thread para salvar a discilpina
        Thread {
            ConteudoService.save(conteudo)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }
}

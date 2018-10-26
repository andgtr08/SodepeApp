package br.com.sodepebrasil.sodepeapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_conteudo.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class CadastroActivity : AppCompatActivity() {
    // Variável de Contexto
    private val context: Context get() = this
    // Variável de conteudo dos cards nula
    var conteudo: Conteudo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        // configurar título com nome da Conteudo e botão de voltar da Toobar
        // colocar toolbar
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // alterar título da ActionBar
        supportActionBar?.title = "Novo cadastro"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Validação dos campos e salvamento do novo cadastro
        salvarConteudo.setOnClickListener {
            val conteudo = Conteudo()
            conteudo.nome = nomeConteudo.text.toString()
            conteudo.ementa = ementaConteudo.text.toString()
            conteudo.professor = professorConteudo.text.toString()
            conteudo.foto = urlFoto.text.toString()

            //Validação dos campos
            if (nomeConteudo.text.toString() == "" || ementaConteudo.text.toString() == "" || professorConteudo.text.toString() == "" || urlFoto.text.toString() == "") {
                Toast.makeText(context, "Digite todos os campos.", Toast.LENGTH_LONG).show()
            }else {
                // passa as informações para o metodo taskAtualizar
                taskAtualizar(conteudo)
            }
        }
    }

    // Metodo que salva as informações no banco
    private fun taskAtualizar(conteudo: Conteudo) {
        // Thread para salvar o conteudo
        Thread {
            ConteudoService.save(conteudo)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }

    // Seleção de item no menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // id do item clicado
        val id = item?.itemId
        // verificar qual item foi clicado
        // remover a conteudo no WS
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

package br.com.sodepebrasil.sodepeapp

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_configuracoes.*

class ConfiguracoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        // colocar toolbar
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        /*
        // Switch de notificações
        switchNotificacoes.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this,"",Toast.LENGTH_SHORT).show()
        }
        */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main, menu)

        //Nomeia a tela.
        supportActionBar?.title = "Configurações"

        //Habilita o botão voltar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Esconde as opções não necessárias na Action Bar.
        //Botão Atualizar.
        val itemAtualizar = menu?.findItem(R.id.action_atualizar)
        itemAtualizar?.isVisible = false
        //Botão Configuração.
        val itemConfig = menu?.findItem(R.id.action_config)
        itemConfig?.isVisible = false
        //Botão Buscar.
        val itemBuscar = menu?.findItem(R.id.action_buscar)
        itemBuscar?.isVisible = false
        //Botão Sobre.
        val itemSobre = menu?.findItem(R.id.action_sobre)
        itemSobre?.isVisible = false
        val itemAdicionar = menu?.findItem(R.id.action_adicionar)
        itemAdicionar?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // id do item clicado
        val id = item?.itemId
        // verificar qual item foi clicado e mostrar a mensagem
        //Toast na tela
        // a comparação é feita com o recurso de id definido no xml

        //Caso seja o botão voltar.
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    //Função para confirmação de saida.
    override fun onBackPressed() {
        this.finish()
    }
}

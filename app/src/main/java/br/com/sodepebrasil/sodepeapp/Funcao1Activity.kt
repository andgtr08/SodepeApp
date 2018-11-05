package br.com.sodepebrasil.sodepeapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class Funcao1Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_home -> {

                //Código para as outras telas
                this.finish()
                val intent = Intent(this, login::class.java)
                //Inicia a activity com os parametros da variável "params"
                startActivityForResult(intent, 1)
            }
            R.id.nav_novo -> {
                val intent = Intent(this, CadastroActivity::class.java)
                //Inicia a activity com os parametros da variável "params"
                startActivityForResult(intent, 1)
            }
            R.id.nav_funcao1 -> {
                val intent = Intent(this, Funcao1Activity::class.java)
                //Inicia a activity com os parametros da variável "params"
                startActivityForResult(intent, 1)
            }
            R.id.nav_mensagens -> {
                Toast.makeText(this, "Clicou Mensagens", Toast.LENGTH_SHORT).show()
            }
            // Função do Botão Configurações na NavDrawer.
            R.id.nav_configuracoes -> {
                val intent = Intent(this, ConfiguracoesActivity::class.java)
                //Inicia a activity com os parametros da variável "params"
                startActivityForResult(intent, 1)
            }
            R.id.nav_sair -> {
                onBackPressed()
            }
        }
        layoutMenuLateral.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funcao1)

        // colocar toolbar
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        configuraMenuLateral()

        var navigationView = menu_lateral
        navigationView.setCheckedItem(R.id.nav_funcao1)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main, menu)

        //Nomeia a tela.
        supportActionBar?.title = "Função 1"

        //Habilita o botão voltar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    private fun configuraMenuLateral() {
        var toolbar = toolbar
        var menuLateral = layoutMenuLateral

        var toogle = ActionBarDrawerToggle(
                this,
                menuLateral,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )

        menuLateral.addDrawerListener(toogle)
        toogle.syncState()

        var navigationView = menu_lateral
        navigationView.setNavigationItemSelectedListener(this)
    }

    //Função para confirmação de saida.
    override fun onBackPressed() {
        if (layoutMenuLateral.isDrawerOpen(GravityCompat.START)) {
            layoutMenuLateral.closeDrawer(GravityCompat.START)
        }
        var navigationView = menu_lateral
        navigationView.setCheckedItem(R.id.nav_home)
        this.finish()
    }
}

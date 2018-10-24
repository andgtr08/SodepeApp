package br.com.sodepebrasil.sodepeapp

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import android.content.DialogInterface
import android.support.v4.widget.DrawerLayout





class login : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_home -> {

                //Código para as outras telas
                /*
                this.finish()
                val intent = Intent(context, login::class.java)
                //Inicia a activity com os parametros da variável "params"
                startActivityForResult(intent, 1)
                */

                //Codigo para a Tela Principal
                Toast.makeText(this, "Você já está na Home", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_novo -> {
                val intent = Intent(context, CadastroActivity::class.java)
                //Inicia a activity com os parametros da variável "params"
                startActivityForResult(intent, 1)
            }
            R.id.nav_funcao1 -> {
                val intent = Intent(context, Funcao1Activity::class.java)
                //Inicia a activity com os parametros da variável "params"
                startActivityForResult(intent, 1)
            }
            R.id.nav_funcao2 -> {
                val intent = Intent(context, Funcao2Activity::class.java)
                //Inicia a activity com os parametros da variável "params"
                startActivityForResult(intent, 1)
            }
            R.id.nav_funcao3 -> {
                val intent = Intent(context, Funcao3Activity::class.java)
                //Inicia a activity com os parametros da variável "params"
                startActivityForResult(intent, 1)
            }
            R.id.nav_mensagens -> {
                Toast.makeText(this, "Clicou Mensagens", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_localizacao -> {
                Toast.makeText(this, "Clicou Localização", Toast.LENGTH_SHORT).show()
            }
            // Função do Botão Configurações na NavDrawer.
            R.id.nav_configuracoes -> {
                val intent = Intent(context, ConfiguracoesActivity::class.java)
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

    //Transforma this em variável Contexto.
    private val context: Context get() = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Variáveis de função dos Botões.
        var botaoFuncao1 = buttonFuncao1
        var botaoFuncao2 = buttonFuncao2
        var botaoFuncao3 = buttonFuncao3
        var botaoFloat = FloatButton

        //Função quando o botão é pressionado.
        botaoFuncao1.setOnClickListener() {
            //Abre uma nota tela.
            val intent = Intent(context, Funcao1Activity::class.java)
            //Inicia a activity com os parametros da variável "params"
            startActivityForResult(intent, 1)
        }

        //Função quando o botão é pressionado.
        botaoFuncao2.setOnClickListener() {
            //Abre uma nota tela.
            val intent = Intent(context, Funcao2Activity::class.java)
            //Inicia a activity com os parametros da variável "params"
            startActivityForResult(intent, 1)
        }

        //Função quando o botão é pressionado.
        botaoFuncao3.setOnClickListener() {
            //Abre uma nota tela.
            val intent = Intent(context, Funcao3Activity::class.java)
            //Inicia a activity com os parametros da variável "params"
            startActivityForResult(intent, 1)
        }

        //Função do Botão Flutuante de cadastro.
        botaoFloat.setOnClickListener(){
            //Abre uma nota tela.
            val intent = Intent(context, CadastroActivity::class.java)
            //Inicia a activity com os parametros da variável "params"
            startActivityForResult(intent, 1)
        }

        // colocar toolbar
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        configuraMenuLateral()
    }

    // função que cria o menu na Actionbar.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main, menu)
        //Renomeia a Actionbar
        supportActionBar?.title = "Tela Inicial"
        //Habilita o botão voltar.
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val itemConfig = menu?.findItem(R.id.action_config)
        itemConfig?.isVisible = false

        //Função da caixa de pesquisa.
        (menu?.findItem(R.id.action_buscar)?.actionView as android.support.v7.widget.SearchView).setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {

            //Função enquanto está digitando.
            override fun onQueryTextChange(newText: String): Boolean {
                //Ação enquanto está digitando o texto.
                Toast.makeText(context, "Digitando...", Toast.LENGTH_LONG).show()
                return false
            }

            //Função quando confirma a pesquisa.
            override fun onQueryTextSubmit(query: String): Boolean {
                //Ação ao confirmar
                Toast.makeText(context, "Pesquisa feita!", Toast.LENGTH_LONG).show()
                return false
            }
        })
        return true
    }

    //Função dos itens da Actionbar.
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // id do item clicado
        val id = item?.itemId
        // verificar qual item foi clicado e mostrar a mensagem
        //Toast na tela
        // a comparação é feita com o recurso de id definido no xml

        //Caso selecionado o botão buscar.
        if (id == R.id.action_buscar) {
            Toast.makeText(context, "Botão de buscar",
                    Toast.LENGTH_LONG).show()
        }
        //Caso selecionado o botão Atualizar.
        else if (id == R.id.action_atualizar) {
            val dialog = ProgressDialog.show(this, "", "Atualizando...", true)
            dialog.show()
            val handler = Handler()
            handler.postDelayed({ dialog.dismiss()
                Toast.makeText(context, "Atualização completa.", Toast.LENGTH_LONG).show()}, 10000) // Delay de 10 Segundos
        }
        //Caso selecionado o botão Voltar.
        else if (id == android.R.id.home) {
            finish()
        }
        //Caso selecionado o botão Sobre.
        else if (id == R.id.action_sobre) {
            val intent = Intent(context, SobreActivity::class.java)
            //Inicia a activity com os parametros da variável "params"
            startActivityForResult(intent, 1)
        }
        return super.onOptionsItemSelected(item)
    }

    // Funções da aplicação.

    //Função para confirmação de saida.
    override fun onBackPressed() {
        if (layoutMenuLateral.isDrawerOpen(GravityCompat.START)) {
            layoutMenuLateral.closeDrawer(GravityCompat.START)
        }

        AlertDialog.Builder(this)
                .setMessage("Tem certeza que deseja sair?")
                .setCancelable(false)
                .setPositiveButton("SIM") { dialog, id -> finishAffinity() }
                .setNegativeButton("NÃO", null)
                .show()
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
}

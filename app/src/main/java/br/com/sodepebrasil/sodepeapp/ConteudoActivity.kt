package br.com.sodepebrasil.sodepeapp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_conteudo.*
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast


class ConteudoActivity : AppCompatActivity() {
    private val context: Context get() = this
    var conteudo: Conteudo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conteudo)

        // recuperar objeto de Conteudo da Intent
        conteudo = intent.getSerializableExtra("conteudo") as Conteudo

        // configurar título com nome da Conteudo e botão de voltar da Toobar
        // colocar toolbar
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // alterar título da ActionBar
        supportActionBar?.title = conteudo?.nome


        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var imagem = findViewById<ImageView>(R.id.imagemConteudo)

        // Verifica se tem internet, e baixa a imagem do cardBox
        if (AndroidUtils.isInternetDisponivel(LMSApplication.getInstance().applicationContext)) {
            if (conteudo?.foto == "") {
                Picasso.with(this).load(R.drawable.ic_empty_background).fit().into(imagem,
                        object: com.squareup.picasso.Callback{
                            override fun onSuccess() {}

                            override fun onError() { }
                        })
            }else {
                Picasso.with(this).load(conteudo?.foto).fit().into(imagem,
                        object: com.squareup.picasso.Callback{
                            override fun onSuccess() {}

                            override fun onError() { }
                        })
            }
        }else{
            // Caso não tenha internet, carrega a imagem local
            Picasso.with(this).load(R.drawable.ic_card_backgroud).fit().into(imagem,
                    object: com.squareup.picasso.Callback{
                        override fun onSuccess() {}

                        override fun onError() { }
                    })
        }
        var ementa = findViewById<TextView>(R.id.nomeEmenta)
        ementa.text = conteudo?.ementa

        //Função do botão Inscrever
        enviarEmail.setOnClickListener {
            val isAppInstalled = appInstalado("com.google.android.gm")

            if(isAppInstalled) {
                val intent = Intent(Intent.ACTION_SEND)
                val recipients = arrayOf("contato@sodepebrasil.com.br")
                intent.putExtra(Intent.EXTRA_EMAIL, recipients)
                intent.putExtra(Intent.EXTRA_SUBJECT, "Inscrição para o evento ${conteudo?.nome}")
                intent.putExtra(Intent.EXTRA_TEXT, "")
                intent.putExtra(Intent.EXTRA_CC, "contato@sodepebrasil.com.br")
                intent.type = "text/html"
                intent.setPackage("com.google.android.gm")
                startActivity(Intent.createChooser(intent, "Enviar e-mail"))
            }else {
                val mapUri = Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.gm")
                val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                startActivity(mapIntent)
                Toast.makeText(this, "Instale o Google Gmail para usar esta função...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Verifica se o App está instalado no dispositivo
    private fun appInstalado(uri: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return false
    }

    // método sobrescrito para inflar o menu na Actionbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main_conteudo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // id do item clicado
        val id = item?.itemId
        // verificar qual item foi clicado
        // remover a conteudo no WS
        if  (id == R.id.action_remover) {
            // alerta para confirmar a remeção
            // só remove se houver confirmação positiva
            AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage("Deseja excluir a conteudo")
                    .setPositiveButton("Sim") {
                        dialog, which ->
                        dialog.dismiss()
                        taskExcluir()
                    }.setNegativeButton("Não") {
                        dialog, which -> dialog.dismiss()
                    }.create().show()
        }
        // botão up navigation
        else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // Metodo de exclusão do item selecionado
    private fun taskExcluir() {
        if (this.conteudo != null && this.conteudo is Conteudo) {
            // Thread para remover a conteudo
            Thread {
                ConteudoService.delete(this.conteudo as Conteudo)
                runOnUiThread {
                    // após remover, voltar para activity anterior
                    finish()
                }
            }.start()
        }
    }
}

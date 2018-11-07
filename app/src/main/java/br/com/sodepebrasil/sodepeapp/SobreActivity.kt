package br.com.sodepebrasil.sodepeapp

import android.Manifest
import android.content.Intent
import android.net.Uri
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
import kotlinx.android.synthetic.main.activity_sobre.*
import kotlinx.android.synthetic.main.toolbar.*
import android.content.pm.PackageManager
import com.google.android.gms.common.wrappers.Wrappers.packageManager
import com.google.android.gms.common.util.ClientLibraryUtils.getPackageInfo
import android.support.v4.app.ActivityCompat
import android.Manifest.permission
import android.Manifest.permission.CALL_PHONE





class SobreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        // colocar toolbar
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // alterar título da ActionBar
        supportActionBar?.title = "Sobre a Sodepe"

        // Ao clicar na imagem do mapa
        imageMap.setOnClickListener{
            // Nome do pacote do Maps
            val isAppInstalled = appInstalado("com.google.android.apps.maps")

            // Verifica se o Google Maps está instalado, caso não, abre o link da PlayStore
            if(isAppInstalled) {
                val mapUri = Uri.parse("http://maps.google.com/maps?saddr=&daddr=-23.532035,-46.682295")
                val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }else {
                val mapUri = Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps")
                val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                startActivity(mapIntent)
                Toast.makeText(this, "Instale o Google Maps para usar esta função...", Toast.LENGTH_SHORT).show()
            }
        }

        // Ao clicar na imagem do do telefone
        imageCall.setOnClickListener{
            //Verifica se o usuário autorizou o uso do telefone
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "11 3872-7485"))
                startActivity(intent)
            } else {
                // Caso não, pede autorização para o usuario.
                val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.CALL_PHONE)
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9)
            }
        }
    }

    // Função que verifica se o app está instalado
    private fun appInstalado(uri: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return false
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
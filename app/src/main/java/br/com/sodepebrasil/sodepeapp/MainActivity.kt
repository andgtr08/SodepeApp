package br.com.sodepebrasil.sodepeapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.widget.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import android.app.ProgressDialog
import android.graphics.Color
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.os.Build





class MainActivity : AppCompatActivity() {

    //Transforma this em variável Contexto.
    private val context: Context get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Desabilita o Title da ActionBar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        window.statusBarColor = Color.WHITE

        var campoUsuario = editTextUsuario
        var campoSenha = editTextSenha
        var botaoSegundaTela = buttonEntrar

        botaoSegundaTela.setOnClickListener() {
            // Caso os campos de usuario e senha estejam em branco, solicitar.
            if (!(campoUsuario.text.isEmpty() || campoSenha.text.isEmpty())) {
                if (campoUsuario.text.toString() == "aluno" && campoSenha.text.toString() == "impacta") {

                    //Cria a notificação de autenticação.
                    val dialog = ProgressDialog.show(this, "", "Autenticando...", true)
                    dialog.show()
                    val handler = Handler()
                    handler.postDelayed({ dialog.dismiss()
                        //Abre uma nota tela.
                        val intent = Intent(context, login::class.java)
                        intent.putExtra("nome", campoUsuario.text.toString())
                        intent.putExtra("numero", 10)
                        //Inicia a activity com os parametros da variável "params"
                        startActivityForResult(intent, 1)
                    }, 1000)
                }else {
                    //Cria a notificação de autenticação.
                    val dialog = ProgressDialog.show(this, "", "Autenticando...", true)
                    dialog.show()
                    val handler = Handler()
                    handler.postDelayed({ dialog.dismiss()
                        Toast.makeText(context, "Usuario ou senha incorretos, tente novamente.", Toast.LENGTH_LONG).show()}, 1000) // Delay de 1000 milisegundos
                }
            }else {
                val dialog = ProgressDialog.show(this, "", "Autenticando...", true)
                dialog.show()
                val handler = Handler()
                handler.postDelayed({ dialog.dismiss()
                    Toast.makeText(context, "Por favor, entre com seu login.", Toast.LENGTH_LONG).show()}, 1000) // Delay de 1000 milisegundos
            }
        }
    }
}
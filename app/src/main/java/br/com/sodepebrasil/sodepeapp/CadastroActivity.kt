package br.com.sodepebrasil.sodepeapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cadastro.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import java.util.*


class CadastroActivity : AppCompatActivity() {

    private val PICK_IMAGE = 100
    var imageUri: Uri? = null

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
        supportActionBar?.title = "Novo evento"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Função de upload de imagem da galeria
        imageViewUpload.setOnClickListener{
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                abrirGaleria()
            }else{
                val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9)
            }
        }

        /*
        // Variável utilizada para a hora
        var mDateSetListener: DatePickerDialog.OnDateSetListener? = null

        // Ao clicar no campo de Data, chama a função de seleção de data
        campoData.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(
                    this,
                    android.R.style.Theme_Material_InputMethod,
                    mDateSetListener,
                    year, month, day)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog.show()
        }

        mDateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var month = month
            month = month + 1

            val date = "Data: " + day + "/" + month.toString() + "/" + year
            campoData.setText(date)
        }
        */

        campoData.setOnClickListener{
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val mTimePicker: DatePickerDialog
            mTimePicker = DatePickerDialog(this, android.R.style.Theme_Material_InputMethod,  DatePickerDialog.OnDateSetListener{datePicker, year, month, day -> campoData.setText(String.format("%02d", day) + "/" + String.format("%02d", month + 1) + "/" + year) }, year, month, day)
            mTimePicker.setTitle("Selecione a data")
            mTimePicker.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            mTimePicker.show()
        }

        // Ao clicar no campo de Hora, chama a função de seleção de data
        campoHora.setOnClickListener{
            campoHora.text = ""
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(this, android.R.style.Theme_Material_InputMethod, TimePickerDialog.OnTimeSetListener { timePicker, hour, minute -> campoHora.setText(String.format("%02d:%02d", hour, minute)) }, hour, minute, true)
            mTimePicker.setTitle("Selecione o Horário")
            mTimePicker.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            mTimePicker.show()
        }

        //Validação dos campos e salvamento do novo cadastro
        salvarConteudo.setOnClickListener {
            val conteudo = Conteudo()
            conteudo.nome = nomeConteudo.text.toString()
            conteudo.ementa = ementaConteudo.text.toString() + "\n\n" + campoData.text.toString() + "\n" + campoHora.text.toString()
            conteudo.professor = professorConteudo.text.toString()
            conteudo.foto = urlFoto.text.toString()

            //Validação dos campos
            if (nomeConteudo.text.toString() == "" || ementaConteudo.text.toString() == "" || professorConteudo.text.toString() == "") {
                Toast.makeText(context, "Digite todos os campos.", Toast.LENGTH_LONG).show()
            }else {
                // passa as informações para o metodo taskAtualizar
                taskAtualizar(conteudo)
                Toast.makeText(this, "Evento cadastrado com sucesso", Toast.LENGTH_SHORT).show()
            }
        }
    }



    // Abrir galeria, para upload de imagem
    private fun abrirGaleria() {
        val gallery = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    // Retorno ao selecionar a imagem
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data?.getData()
            imageViewUpload.setImageURI(imageUri)
            urlFoto.setText(imageUri.toString())
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

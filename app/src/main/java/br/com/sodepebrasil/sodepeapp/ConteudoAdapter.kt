package br.com.sodepebrasil.sodepeapp

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Picasso

// define o construtor que recebe a lista de conteudos e o evento de clique
class ConteudoAdapter (
        val conteudos: List<Conteudo>,
        val onClick: (Conteudo) -> Unit): RecyclerView.Adapter<ConteudoAdapter.ConteudosViewHolder>() {

    // ViewHolder com os elemetos da tela
    class ConteudosViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardNome: TextView
        val cardImg : ImageView
        var cardProgress: ProgressBar
        var cardView: CardView

        init {
            cardNome = view.findViewById<TextView>(R.id.cardNome)
            cardImg = view.findViewById<ImageView>(R.id.cardImg)
            cardProgress = view.findViewById<ProgressBar>(R.id.cardProgress)
            cardView = view.findViewById<CardView>(R.id.card_conteudos)
        }
    }

    // Quantidade de conteudos na lista

    override fun getItemCount() = this.conteudos.size

    // inflar layout do adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConteudosViewHolder {
        // infla view no adapter
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_conteudo, parent, false)

        // retornar ViewHolder
        val holder = ConteudosViewHolder(view)
        return holder
    }

    // bind para atualizar Views com os dados

    override fun onBindViewHolder(holder: ConteudosViewHolder, position: Int) {
        val context = holder.itemView.context

        // recuperar objeto conteudo
        val conteudo = conteudos[position]

        // atualizar dados de conteudo
        holder.cardNome.text = conteudo.nome
        holder.cardProgress.visibility = View.VISIBLE

        // Carregando imagem do card, baseando na conexão de internet
        if (conteudo.foto == "") {
            if (AndroidUtils.isInternetDisponivel(LMSApplication.getInstance().applicationContext)) {
                Picasso.with(context).load(R.drawable.ic_empty_background).fit().into(holder.cardImg,
                        object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                holder.cardProgress.visibility = View.GONE
                            }

                            override fun onError() {
                                holder.cardProgress.visibility = View.GONE
                            }
                        })
            }else {
                Picasso.with(context).load(R.drawable.ic_card_backgroud).fit().into(holder.cardImg,
                        object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                holder.cardProgress.visibility = View.GONE
                            }

                            override fun onError() {
                                holder.cardProgress.visibility = View.GONE
                            }
                        })
            }

        }

        // download da imagem
        if (conteudo.foto != "") {
            // Caso tenha acesso a internet, baixa do servidor.
            if (AndroidUtils.isInternetDisponivel(LMSApplication.getInstance().applicationContext)) {
                Picasso.with(context).load(conteudo.foto).fit().into(holder.cardImg,
                        object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                holder.cardProgress.visibility = View.GONE
                            }

                            override fun onError() {
                                holder.cardProgress.visibility = View.GONE
                            }
                        })
            }else {
                if (conteudo.foto == "") {
                    Picasso.with(context).load(R.drawable.ic_card_backgroud).fit().into(holder.cardImg,
                            object : com.squareup.picasso.Callback {
                                override fun onSuccess() {
                                    holder.cardProgress.visibility = View.GONE
                                }

                                override fun onError() {
                                    holder.cardProgress.visibility = View.GONE
                                }
                            })
                }else {
                    // Caso não tenha acesso a internet, carrega a imagem local.
                    Picasso.with(context).load(R.drawable.ic_card_backgroud).fit().into(holder.cardImg,
                            object : com.squareup.picasso.Callback {
                                override fun onSuccess() {
                                    holder.cardProgress.visibility = View.GONE
                                }

                                override fun onError() {
                                    holder.cardProgress.visibility = View.GONE
                                }
                            })
                }
            }
        }

        // adiciona evento de clique
        holder.itemView.setOnClickListener {onClick(conteudo)}
    }
}
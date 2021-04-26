package holguin.daniel.publicsubscribe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import Envia.Envia
import Recibe.Recibe
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    val threadEnvia = Runnable {
        var  envia = Envia("JUEGOS")
        var  recibe = Recibe("JUEGOS")

        envia.envia(et_envia.text.toString())
        Log.d("Test Envia, ", et_envia.text.toString())
        recibe.recibe()
        var texto : String = recibe.recibe()
        tv_recibe.setText(texto)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_enivar.setOnClickListener {
            val thread = Thread(threadEnvia)
            thread.start()
        }
    }
}
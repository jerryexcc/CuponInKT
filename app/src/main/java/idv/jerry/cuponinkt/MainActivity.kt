package idv.jerry.cuponinkt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import idv.jerry.cuponinkt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val map = HashMap<String, List<String>>()
    private val arraylist = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        map["國旅劵"] = listOf("21", "32", "98", "67", "97", "410")
        map["i原劵"] = listOf("64", "85")
        map["農遊劵"] = listOf("89", "32", "54", "597", "453", "152")
        map["藝fun劵"] = listOf("96", "15", "07", "30", "73", "98", "19", "11")
        map["藝fun劵(紙本)"] = listOf("39", "37", "23", "36", "79", "08", "14", "75")
        map["動滋劵"] = listOf("97", "13", "19", "55", "71", "93", "381", "734", "644", "453", "985")
        map["客庄劵2.0"] = listOf("81", "900")

        binding.btnQuery.setOnClickListener {
            if (arraylist.isNotEmpty()) {
                arraylist.clear()
            }
            map.forEach { (str, list) ->
                list.forEach {
                    if (it == binding.edNameInput.text.toString() ) {
                        arraylist.add(str)
                    }
                }
            }

            if (arraylist.isNotEmpty()) {
                arraylist.forEach {
                    Log.w("TAG", it)
                }
            } else {
                Log.w("TAG", "沒有中...")
            }
        }
    }
}
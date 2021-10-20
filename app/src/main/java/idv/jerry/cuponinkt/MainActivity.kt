package idv.jerry.cuponinkt

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import idv.jerry.cuponinkt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val map = HashMap<String, List<String>>()
    private val arraylist = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMap()

        binding.btnQuery.setOnClickListener {
            var inputText = try {
                binding.edNameInput.text.toString()
            } catch (e: Exception) {
                ""
            }
            try {
                inputText.substring(inputText.length - 2).toInt()
            } catch (e: Exception) {
                Snackbar.make(it, "請輸入數字", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (inputText.length > 3) {//不管輸入什麼就只留最後三碼
                inputText = inputText.substring(inputText.length - 3)
            } else if (inputText.isEmpty()) {
                Snackbar.make(it, "請輸入點東西吧", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.tvResultTitle.visibility = View.INVISIBLE
            if (arraylist.isNotEmpty()) {
                arraylist.clear()
            }
            map.forEach { (str, list) ->
                list.forEach { mapString ->
                    when (mapString.length) {
                        2 -> { //中獎號碼 = 2碼
                            //重新裁剪字串至2碼
                            val subString = inputText.substring(inputText.length - 2)
                            if (mapString == subString) {//直接字串比對
                                arraylist.add(str)
                            }
                        }
                        3 -> { //中獎號碼 = 3碼
                            if (mapString.endsWith(inputText) && mapString == inputText) {
                                arraylist.add(str)
                            }
                        }
                    }
                }
            }

            if (arraylist.isNotEmpty()) {
                with(StringBuilder()) {
                    binding.tvResultTitle.visibility = View.VISIBLE
                    arraylist.forEach { str ->
                        this.append("$str \n")
                    }
                    binding.tvResult.text = this.toString()
                }
            } else {
                binding.tvResult.text = "沒有中..."
            }
        }
    }

    private fun initMap() {
        map["國旅劵"] = listOf("21", "32", "98", "67", "97", "410")
        map["i原劵"] = listOf("64", "85")
        map["農遊劵"] = listOf("89", "32", "54", "597", "453", "152")
        map["藝fun劵"] = listOf("96", "15", "07", "30", "73", "98", "19", "11")
        map["藝fun劵(紙本)"] = listOf("39", "37", "23", "36", "79", "08", "14", "75")
        map["動滋劵"] = listOf("97", "13", "19", "55", "71", "93", "381", "734", "644", "453", "985")
        map["客庄劵2.0"] = listOf("81", "900")
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
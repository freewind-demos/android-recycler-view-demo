package demos.android.recycler.view.demo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val items = listOf("项目1", "项目2", "项目3", "项目4", "项目5")
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(items)
    }
}

class MyAdapter(private val items: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view)
    
    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val textView = TextView(parent.context)
        textView.setPadding(32, 32, 32, 32)
        return ViewHolder(textView)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as TextView).text = items[position]
    }
    
    override fun getItemCount(): Int = items.size
}

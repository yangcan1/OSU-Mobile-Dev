import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.R
import com.example.foodrecipe.data.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class FoodSearchAdapter : RecyclerView.Adapter<FoodSearchAdapter.ViewHolder>() {
    private var meals: List<Meal> = listOf()

    private lateinit var cardListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int, activeMeal: Meal)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        //Sets local listener to the one passed in
        cardListener = listener
    }

    fun updateMealList(newRepoList: List<Meal>?) {
        notifyItemRangeRemoved(0, meals.size)
        meals = newRepoList ?: listOf()
        notifyItemRangeInserted(0, meals.size)
    }

    override fun getItemCount() = meals.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_search_list, parent, false)
        return ViewHolder(view, cardListener)
    }


    // On long click listener sources
    // Video: Setting up a click listener for recycler view
    // https://www.youtube.com/watch?v=WqrpcWXBz14
    var onItemLongClick : ((Meal) -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(meals[position])

        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(meals[position])
            true
        }
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        private val titleTV: TextView = view.findViewById(R.id.tv_title)
        private val imgIV: ImageView = view.findViewById(R.id.iv_image)
        private lateinit var activeMeal: Meal

        init {
            itemView.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition, activeMeal)
            }
        }

        fun bind(meal: Meal) {
            titleTV.text = meal.title
            loadImageFromInternet(meal.image)
            activeMeal = meal
        }

        private fun loadImageFromInternet(imageUrl: String) {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val bitmap = withContext(Dispatchers.IO) {
                        downloadImage(imageUrl)
                    }
                    imgIV.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    Log.e("Error Message", e.message.toString())
                    e.printStackTrace()
                }
            }
        }

        private suspend fun downloadImage(imageUrl: String): Bitmap {
            var inputStream: InputStream? = null
            try {
                inputStream = URL(imageUrl).openStream()
                return BitmapFactory.decodeStream(inputStream)
            } finally {
                inputStream?.close()
            }
        }
    }
}

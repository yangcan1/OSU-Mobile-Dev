// Inside adapters package (create if not exists)

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.R
import com.example.foodrecipe.data.ExtendIngredient
import com.example.foodrecipe.data.Meal

class IngredientAdapter(private var ingredients: List<ExtendIngredient>) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    fun updateMealList(newIngredientList: List<ExtendIngredient>?) {
        notifyItemRangeRemoved(0, ingredients.size)
        ingredients = newIngredientList ?: listOf()
        notifyItemRangeInserted(0, ingredients.size)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = ingredients.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientName: TextView = itemView.findViewById(R.id.tv_ingredient)

        fun bind(ingredient: ExtendIngredient) {
            ingredientName.text = ingredient.original
        }
    }
}
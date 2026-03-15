# Android RecyclerView 演示

## 简介

本 Demo 演示 RecyclerView 的基本用法，展示如何创建高性能的列表视图。

## 基本原理

RecyclerView 是 Android 5.0 引入的列表组件，是 ListView 的升级版。它采用 ViewHolder 模式和回收机制优化性能，支持多种布局方式（线性、网格、瀑布流）。

RecyclerView 的核心组件：
- **Adapter**：提供数据和处理 item 视图
- **ViewHolder**：缓存视图，提高性能
- **LayoutManager**：管理 item 的布局方式
- **ItemDecoration**：添加分割线和装饰
- **ItemAnimator**：处理 item 的动画效果

## 启动和使用

### 环境要求
- Android Studio
- JDK 17
- Gradle 8.x
- RecyclerView 库

### 安装和运行

1. 用 Android Studio 打开项目
2. 连接 Android 设备或模拟器
3. 点击 Run 运行

### 使用方法
- 上下滑动查看列表
- RecyclerView 支持高效的列表滚动

## 教程

### 什么是 RecyclerView？

RecyclerView 是 Android 官方推荐的列表组件，相比 ListView 有以下优势：
- ViewHolder 模式内置，性能更好
- 支持多种布局管理器
- 支持添加 item 动画
- 支持分割线装饰
- 更灵活的点击事件处理

### LayoutManager 类型

RecyclerView 支持三种布局管理器：

1. **LinearLayoutManager**：线性布局（垂直/水平）
2. **GridLayoutManager**：网格布局
3. **StaggeredGridLayoutManager**：瀑布流布局

```kotlin
// 垂直列表
recyclerView.layoutManager = LinearLayoutManager(this)

// 水平列表
recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

// 网格布局（2列）
recyclerView.layoutManager = GridLayoutManager(this, 2)
```

### 创建 Adapter

```kotlin
class MyAdapter(private val items: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // 1. 创建 ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = TextView(parent.context)
        textView.setPadding(32, 32, 32, 32)
        return ViewHolder(textView)
    }

    // 2. 绑定数据到 ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as TextView).text = items[position]
    }

    // 3. 返回 item 数量
    override fun getItemCount(): Int = items.size

    // ViewHolder 类
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
```

### 设置 RecyclerView

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
val items = listOf("项目1", "项目2", "项目3", "项目4", "项目5")

// 设置布局管理器
recyclerView.layoutManager = LinearLayoutManager(this)

// 设置适配器
recyclerView.adapter = MyAdapter(items)

// 可选：设置分割线
recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
```

### 点击事件处理

在 Adapter 中添加点击监听：

```kotlin
class MyAdapter(private val items: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as TextView).text = items[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(position)
        }
    }

    // ... 其他方法
}
```

### 注意事项

1. **ViewHolder**：必须实现，否则性能很差
2. **notifyDataSetChanged**：数据变化时调用刷新列表
3. **DiffUtil**：大量数据变化时使用 DiffUtil 提高效率
4. **ViewHolder 复用**：onBindViewHolder 中要绑定所有数据

## 关键代码详解

### MainActivity.kt

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 获取 RecyclerView 组件
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // 2. 准备数据列表
        val items = listOf("项目1", "项目2", "项目3", "项目4", "项目5")

        // 3. 设置布局管理器
        // LinearLayoutManager：垂直线性布局
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 4. 设置适配器
        recyclerView.adapter = MyAdapter(items)
    }
}
```

### MyAdapter.kt

```kotlin
class MyAdapter(private val items: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // 1. ViewHolder：缓存视图，提高性能
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // 2. 创建 ViewHolder
    // 当需要新的 ViewHolder 时调用
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 动态创建 TextView 作为 item 布局
        val textView = TextView(parent.context)
        textView.setPadding(32, 32, 32, 32)
        return ViewHolder(textView)
    }

    // 3. 绑定数据
    // 当 ViewHolder 需要显示数据时调用
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 将 TextView 的文本设置为对应位置的数据
        (holder.itemView as TextView).text = items[position]
    }

    // 4. 返回 item 数量
    override fun getItemCount(): Int = items.size
}
```

### activity_main.xml

```xml
<!-- 根布局：垂直线性布局 -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <!-- 标题 -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="RecyclerView 演示"
        android:textSize="20sp"
        android:textStyle="bold"
        android:gravity="center"
        android:paddingBottom="16dp" />

    <!-- RecyclerView 组件 -->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```

### RecyclerView vs ListView

| 特性 | RecyclerView | ListView |
|------|--------------|----------|
| ViewHolder | 内置 | 需手动实现 |
| 布局管理器 | 支持多种 | 仅垂直/水平 |
| 动画支持 | 内置 | 需手动 |
| 分割线 | ItemDecoration | android:divider |
| 性能 | 更好 | 一般 |

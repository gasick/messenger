package com.example.messenger.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.ui.data.local.AppPreferences
import com.example.messenger.ui.data.vo.ConversationVO
import com.example.messenger.ui.data.vo.UserVO
import com.example.messenger.ui.login.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var llContainer: LinearLayout
    private lateinit var presenter: MainPresenter

    private val contactsFragment = ContactsFragment()
    private val conversationsFragment = ConversationsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenterImpl(this)

        conversationsFragment.setActivity(this)
        contactsFragment.setActivity(this)

        bindViews()
        showConversationScreen()
    }

    override fun bindViews(){
        llContainer = findViewById(R.id.ll_container)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun showConversationsLoadError(){
        Toast.makeText(this, "Unable to load conversations. Try again later.", Toast.LENGTH_LONG).show()
    }

    override fun showContactsLoadError(){
        Toast.makeText(this, "Unable to load contacts. Try again later.", Toast.LENGTH_LONG).show()
    }

    override fun showConversationsScreen() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.ll_container, conversationsFragment)
        fragmentTransaction.commit()

        presenter.loadConversations()

        supportActionBar?.title = "Messenger"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun showContactsScreen(){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.ll_container, contactsFragment)
        fragmentTransaction.commit()

        presenter.loadContacts()

        supportActionBar?.title = "Contacts"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showNoConversations(){
        Toast.makeText(this, "You have no active conversations.", Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId){
            android.R.id.home -> showConversationsScreen()
            R.id.action_settings -> navigateToSettings()
            R.id.action_logout -> presenter.executeLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getContext(): Context{
        return this
    }

    override fun getContactsFragment(): ContactsFragment{
        return contactsFragment
    }

    override fun getConversationFragment(): ConversationsFragment{
        return conversationsFragment
    }

    override fun navigateToLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun navigateToSettins(){
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}


class ConversationsFragment: Fragment(), View.OnClickListener{
    private lateinit var activity: MainActivity
    private lateinit var rvConversations: RecyclerView
    private lateinit var fabContacts: FloatingActionButton
    var conversations: ArrayList<ConversationVO> = ArrayList()
    lateinit var conversationsAdapter: ConversationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View? {
        val baseLayout= inflater.inflate(R.layout.fragment_conversations, container, false)
        rvConversations = baseLayout.findViewById(R.id.rv_conversations)
        fabContacts = baseLayout.findViewById(R.id.fab_contacts)

        conversationsAdapter = ConversationsAdapter( getActivity(), conversations)

        rvConversations.adapter = conversationsAdapter
        rvConversations.layoutManager = LinearLayoutManager(getActivity().baseContext)
        fabContacts.setOnClickListener(this)
        return baseLayout
    }

    override fun onClick(v: View) {
        if (view.id == R.id.fab_contacts){
           this.activity.showContactsScreen()
        }
    }

    fun setActivity(activity: MainActivity){
        this.activity = activity
    }
}


class ContactsFragment: Fragment() {
    private lateinit var activity: MainActivity
    private lateinit var rvContacts: RecyclerView
    var contacts: ArrayList<UserVO> = ArrayList()
    lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View? {
        val baseLayout = inflater.inflate(
            R.layout.fragment_contacts,
            container,
            false
        )
        rvContacts = baseLayout.findViewById(R.id.rv_contacts)
        contactsAdapter = ContactsAdapter(getActivity(), contacts)

        rvContacts.adapter = contactsAdapter
        rvContacts.layoutManager = LinearLayoutManager(getActivity().baseContext)

        return baseLayout
    }

    fun setActivity(activity: MainActivity){
        this.activity = activity
    }
}

class ContactsAdapter(
    private val context: Context,
    private val dataSet: List<UserVO>
    ):
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>(),
    ChatView.ChatAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.vh_contacts, parent, false)
        val llContainer = itemLayout.findViewById<LinearLayout>(R.id.ll_container)

        return ViewHolder(llContainer)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        val itemLayout = holder.itemLayout

        itemLayout.findViewById<TextView>(R.id.tv_username).text = item.username
        itemLayout.findViewById<TextView>(R.id.tv_phone).text = item.phoneNumber
        itemLayout.findViewById<TextView>(R.id.tv_status).text = item.status

        itemLayout.setOnClickListener {
            navigateToChat(item.username, item.id)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun navigateToChat(
        recipientName: String,
        recipientId: Long,
        conversationId: Long?
    ) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("RECIPIENT_ID", recipientId)
        intent.putExtra("RECIPIENT_NAME", recipientName)

        context.startActivity(intent)
    }

    class ViewHolder(val itemLayout: LinearLayout) : RecyclerView.ViewHolder(itemLayout)

}


class ConversationsAdapter(
    private val context: Context,
    private val dataSet: List<ConversationVO>
):
    RecyclerView.Adapter<ConversationsAdapter.ViewHolder>(),
        ChatView.ChatAdapter {
    val preferences: AppPreferences = AppPreferences.create(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = dataSet[position]
       val itemLayout = holder.itemLayout

        itemLayout.findViewById<TextView>(R.id.tv_username).text = item.secondPartyUsername
        itemLayout.findViewById<TextView>(R.id.tv_preview).text = item.messages[item.messages.size -1].body

        itemLayout.setOnClickListener{
            val message = item.messages[0]
            val recipientId: Long
            recipientId = if (message.senderId == preferences.userDetails.id) {
                message.recipientId
            }else {
                message.senderId
            }

            navigateToChat(
                item.secondPartyUsername,
                recipientId,
                item.conversationId
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversationsAdapter.ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.vh_conversations, null, false)
            .findViewById<LinearLayout>(R.id.ll_container)

        return ViewHolder(itemLayout)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun navigateToChat(
        recipientName: String,
        recipientId: Long,
        conversationId: Long?
    ) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("CONVERSATION_ID", conversationId)
        intent.putExtra("RECIPIENT_DI", recipientId)
        intent.putExtra("RECIPIENT_NAME", recipientName)

        context.startActivity(intent)
    }

    class ViewHolder(val itemLayout: LinearLayout): RecyclerView.ViewHolder(itemLayout)

}




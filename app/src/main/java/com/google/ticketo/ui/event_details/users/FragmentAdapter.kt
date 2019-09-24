package com.google.ticketo.ui.event_details.users

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.ticketo.ui.event_details.users.users_list.UsersListView

class FragmentAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = mutableListOf<UsersListView>()
    private val titles = mutableListOf<String>()

    override fun getItem(position: Int): UsersListView = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    fun addFragment(fragment: UsersListView, title: String) {
        fragmentList.add(fragment)
        titles.add(title)
    }

    fun usersExists(groupId: String): Boolean = titles.contains(groupId)

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}
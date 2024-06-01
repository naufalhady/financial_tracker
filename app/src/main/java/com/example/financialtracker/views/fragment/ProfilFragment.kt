package com.example.financialtracker.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.financialtracker.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set profile information
        binding.apply {
            profileName.text = "Muhammad Naufal Hady Anshari Jaelani"
            profileNpm.text = "NPM: 5210411365"
            profileTitle.text = "Judul: Perancangan Aplikasi Financial Tracker berbasis mobile menggunakan design pattern MVVM"
        }
    }
}

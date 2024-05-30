/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author DE
 */


import java.util.List;
import util.Trie;

public class BusquedaService {
    private Trie trie;

    public BusquedaService(List<String> usuarios) {
        this.trie = new Trie();
        for (String usuario : usuarios) {
            trie.insert(usuario);
        }
    }

    public List<String> buscarUsuarios(String prefix) {
        return trie.search(prefix);
    }
}


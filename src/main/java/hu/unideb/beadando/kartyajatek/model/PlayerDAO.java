/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.beadando.kartyajatek.model;

/**
 *
 * @author szilvacsku
 */
public interface PlayerDAO {
    
    public Player getRegisteredPlayer(String nickName);
    public void registerPlayer(Player player);
    public void removePlayer(Player player);
    
}

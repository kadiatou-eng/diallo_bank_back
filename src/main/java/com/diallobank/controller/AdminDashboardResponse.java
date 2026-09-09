package com.diallobank.controller;

import com.diallobank.model.Compte;
import com.diallobank.model.Pret;
import java.util.List;

public class AdminDashboardResponse {
    private List<Compte> comptes;
    private List<Pret> prets;

    public AdminDashboardResponse(List<Compte> comptes, List<Pret> prets) {
        this.comptes = comptes;
        this.prets = prets;
    }

    public List<Compte> getComptes() { return comptes; }
    public void setComptes(List<Compte> comptes) { this.comptes = comptes; }

    public List<Pret> getPrets() { return prets; }
    public void setPrets(List<Pret> prets) { this.prets = prets; }
}

package com.medtech.model.componente.memoria;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.medtech.individual.Gmailer;

public class MonitoramentoMemoria {
    private Looca looca = new Looca();
    private Memoria memoria = looca.getMemoria();
    private static final long INTERVALO_ALERTA = 10 * 60 * 1000; // 10 minutos
    private static long ultimoAlerta = 0;

    Gmailer gmailer = new Gmailer();

    public MonitoramentoMemoria() throws Exception {
    }

    public double getMemoriaTotalGB() {
        return memoria.getTotal() / (1024.0 * 1024.0 * 1024.0);
    }

    public double getMemoriaEmUsoGB() {
        return memoria.getEmUso() / (1024.0 * 1024.0 * 1024.0);
    }

    public double getMemoriaDisponivelGB() {
        return memoria.getDisponivel() / (1024.0 * 1024.0 * 1024.0);
    }

    public double getPorcentagemMemoriaEmUso() {
        return (memoria.getEmUso() / (double) memoria.getTotal()) * 100;
    }

    public void exibeMemoria() {
        System.out.printf("Memória Total: %.2f GB%n", getMemoriaTotalGB());
        System.out.printf("Memória Disponível: %.2f GB%n", getMemoriaDisponivelGB());
        System.out.printf("Memória em Uso: %.2f GB%n", getMemoriaEmUsoGB());
        System.out.printf("Porcentagem de Memória em Uso: %.2f%%%n", getPorcentagemMemoriaEmUso());
    }

    public void verificaMemoria() throws Exception {
        double porcentagemEmUso = getPorcentagemMemoriaEmUso();
        long tempoAtual = System.currentTimeMillis();

        if (porcentagemEmUso > 10) {
            if (tempoAtual - ultimoAlerta >= INTERVALO_ALERTA) {
                String mensagem = "ALERTA: Memória RAM em uso ultrapassou 90%!";
                ultimoAlerta = tempoAtual;

                gmailer.sendEmail("ALERTA", mensagem, "kaua.nunes1225@gmail.com");

            }
        }
    }
}

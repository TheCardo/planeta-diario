package com.ricardo.PlanetaDiario.service.subscribers;

import com.ricardo.PlanetaDiario.events.NovaPublicacaoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoSubscriber {

    @EventListener
    public void processarNovaPublicacao(NovaPublicacaoEvent evento) {

        System.out.println("\n=======================================================");
        System.out.println(" 📡 [PUB/SUB ATIVADO] NOTIFICAÇÃO ENVIADA AOS LEITORES");
        System.out.println("=======================================================");
        System.out.println(" O repórter " + evento.nomeDoAutor() + " acaba de publicar um novo dossiê!");
        System.out.println(" Título: " + evento.tituloDaPublicacao());
        System.out.println("=======================================================\n");
    }
}
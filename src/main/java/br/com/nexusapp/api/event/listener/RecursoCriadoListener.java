package br.com.nexusapp.api.event.listener;

import br.com.nexusapp.api.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApplicationEvent(RecursoCriadoEvent event) {
        this.setHeaderLocation(event);
    }

    private void setHeaderLocation(RecursoCriadoEvent event) {
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(event.getCodigo()).toUri();
        event.getResponse().setHeader("Location", uri.toASCIIString());
    }
}
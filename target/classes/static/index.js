document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const btn = document.getElementById('treeToggle');
    const input = document.getElementById('showTreeInput');
    const textarea = document.getElementById('sentence');

    // Focus iniziale sulla textarea
    if (textarea) textarea.focus();

    // Toggle ON/OFF + rimozione focus dal bottone
    btn.addEventListener('click', () => {
        const on = btn.getAttribute('aria-pressed') === 'true';
        btn.setAttribute('aria-pressed', String(!on));
        input.value = String(!on);
        btn.querySelector('.toggle-text').textContent = !on
            ? 'Syntactic Tree: ON'
            : 'Syntactic Tree: OFF';
        btn.blur(); // evita che Enter successivo ri-attivi il toggle
    });

    // Il toggle non reagisce a Enter
    btn.addEventListener('keydown', (e) => {
        if (e.key === 'Enter' || e.keyCode === 13) e.preventDefault();
    });

    // Nella textarea: Enter invia, Shift+Enter va a capo
    textarea.addEventListener('keydown', (e) => {
        if ((e.key === 'Enter' || e.keyCode === 13) && !e.shiftKey) {
            e.preventDefault();
            form.requestSubmit(); // migliore di form.submit()
        }
    });

    // Fallback globale: se premi Enter e NON sei su un bottone, invia il form
    document.addEventListener('keydown', (e) => {
        if ((e.key === 'Enter' || e.keyCode === 13) && !e.shiftKey) {
            const el = document.activeElement;
            const isToggle = el === btn;
            const isButton = el && el.tagName === 'BUTTON';
            if (!isToggle && !isButton) {
                e.preventDefault();
                form.requestSubmit();
            }
        }
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('errModal');
    const dialog = modal?.querySelector('.modal__dialog');
    const firstClose = modal?.querySelector('[data-close]');

    if (!modal) return;

    // Se Thymeleaf ha messo la classe is-open (c'è un error), rendi visibile il modal e gestisci focus
    const isOpen = modal.classList.contains('is-open');

    function closeModal() {
        modal.classList.remove('is-open');
        modal.setAttribute('aria-hidden', 'true');
        // restituisci focus alla textarea
        const ta = document.getElementById('sentence');
        ta && ta.focus();
    }

    function openModal() {
        modal.classList.add('is-open');
        modal.setAttribute('aria-hidden', 'false');
        // porta focus sul dialog
        setTimeout(() => { dialog?.focus(); }, 10);
    }

    // click su backdrop / X / OK
    modal.addEventListener('click', (e) => {
        if (e.target instanceof Element && e.target.hasAttribute('data-close')) {
            e.preventDefault();
            closeModal();
        }
    });

    // ESC per chiudere
    document.addEventListener('keydown', (e) => {
        if (modal.classList.contains('is-open') && e.key === 'Escape') {
            e.preventDefault();
            closeModal();
        }
    });

    // Se è aperto all'arrivo (flash attribute presente)
    if (isOpen) openModal();

    // (opzionale) autoclose dopo 4s
    // if (isOpen) setTimeout(closeModal, 4000);
});
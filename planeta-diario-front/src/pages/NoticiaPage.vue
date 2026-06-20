<template>
  <div class="min-h-screen bg-[#F9F9F6] text-[#1C1C1E] pb-12 relative">
    
    <transition name="slide-down">
      <div v-if="toast.show" 
           class="fixed top-8 left-1/2 -translate-x-1/2 z-[100] px-8 py-4 shadow-2xl font-sans font-bold text-sm text-center border-b-4"
           :class="toast.type === 'error' ? 'bg-[#1C1C1E] text-red-400 border-red-600' : 'bg-[#1C1C1E] text-[#CBA052] border-[#CBA052]'">
        {{ toast.message }}
      </div>
    </transition>

    <header class="max-w-4xl mx-auto px-4 pt-6 pb-4 border-b border-[#1C1C1E]/20 mb-8 flex justify-between items-center">
      <button @click="router.push('/')" class="text-xs font-bold uppercase tracking-widest hover:text-[#0A3161] transition-colors flex items-center gap-2">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-4 h-4">
          <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 19.5 3 12m0 0 7.5-7.5M3 12h18" />
        </svg>
        Voltar à Capa
      </button>
      <h1 class="font-serif text-3xl font-black uppercase tracking-tighter">O Planeta Diário</h1>
      <div class="w-24"></div> 
    </header>

    <main v-if="noticia" class="max-w-4xl mx-auto px-4 animate-fade-in">
      
      <article class="mb-12 border-b-4 border-[#1C1C1E] pb-8">
        <span class="text-[10px] font-bold uppercase tracking-widest text-[#CBA052] mb-2 block">Fonte Externa: {{ noticia.autor || 'Agência' }}</span>
        <h1 class="font-serif text-4xl md:text-5xl font-black leading-tight mb-6">{{ noticia.titulo }}</h1>
        <img :src="noticia.imagem" class="w-full h-auto md:h-[400px] object-cover grayscale border-2 border-black/10 mb-6 shadow-md">
        <p class="font-sans text-xl text-[#1C1C1E]/80 leading-relaxed mb-6 border-l-4 border-[#0A3161] pl-4 italic">
          {{ noticia.resumo }}
        </p>
        <a :href="noticia.id" target="_blank" class="inline-block bg-[#1C1C1E] text-white px-6 py-2 text-xs font-bold uppercase tracking-widest hover:bg-[#0A3161] transition-colors">
          Ler Fonte Original
        </a>
      </article>

      <section v-if="isCriador" class="bg-[#E5E5E1] p-8 border border-[#1C1C1E]/20 shadow-inner mb-12 relative">
        <div class="absolute -top-3 left-8 bg-[#0A3161] text-white px-3 py-1 text-[10px] font-black uppercase tracking-widest shadow-sm">
          Área do Repórter
        </div>
        <h2 class="font-serif text-2xl font-bold mb-4 mt-2">Redigir Novo Dossiê</h2>
        <form @submit.prevent="publicarDossie" class="space-y-4">
          <div>
            <input v-model="form.titulo" type="text" placeholder="Título da sua matéria..." class="w-full bg-transparent border-b-2 border-[#1C1C1E]/50 py-2 focus:outline-none focus:border-[#0A3161] font-serif text-xl font-bold" required>
          </div>
          <div>
            <textarea v-model="form.conteudo" rows="6" placeholder="Escreva o conteúdo completo aqui..." class="w-full bg-[#F9F9F6] border border-[#1C1C1E]/20 p-4 focus:outline-none focus:border-[#0A3161] font-sans text-sm resize-y" required></textarea>
          </div>
          <button type="submit" :disabled="isPublishing" class="bg-[#CBA052] text-[#1C1C1E] px-8 py-3 text-xs font-black uppercase tracking-widest hover:bg-[#b08840] transition-colors shadow-md disabled:opacity-50">
            {{ isPublishing ? 'A Transmitir...' : 'Publicar Dossiê' }}
          </button>
        </form>
      </section>

      <section>
        <h2 class="font-serif text-3xl font-black uppercase tracking-tighter mb-8 flex items-center gap-4">
          Dossiês do Planeta Diário
          <span class="h-px bg-[#1C1C1E]/20 flex-grow"></span>
        </h2>
        
        <div v-if="publicacoes.length === 0" class="text-center py-12 text-black/40 italic font-serif text-lg">
          Nenhum repórter cobriu este assunto ainda.
        </div>

        <div class="space-y-8">
          <article v-for="pub in publicacoes" :key="pub.id" class="border border-[#1C1C1E]/10 p-6 bg-white shadow-sm hover:shadow-md transition-shadow">
            <div class="flex items-center gap-3 mb-4 border-b border-black/5 pb-3">
              <div class="w-8 h-8 rounded-full bg-[#0A3161] text-white flex items-center justify-center font-bold font-serif shadow-inner">
                {{ (pub.autorNome || pub.autorUsername).charAt(0).toUpperCase() }}
              </div>
              <div>
                <div class="flex items-center gap-2">
                  <div class="font-bold text-sm">{{ pub.autorNome || pub.autorUsername }}</div>
                  
                  <button @click="router.push(`/autor/${pub.autorUsername}`)" class="text-[8px] border border-[#0A3161] text-[#0A3161] px-2 py-0.5 uppercase tracking-widest font-black hover:bg-[#0A3161] hover:text-white transition-colors">
                    Acessar Perfil
                  </button>
                  
                </div>
                <div class="text-[9px] uppercase tracking-widest text-[#CBA052] font-black">{{ pub.autorProfissao || 'Jornalista' }}</div>
              </div>
              <div class="ml-auto text-xs text-black/40">{{ formatarData(pub.dataDeCriacao) }}</div>
            </div>
            <h3 class="font-serif text-2xl font-bold mb-3">{{ pub.titulo }}</h3>
            <p class="font-sans text-[#1C1C1E]/80 whitespace-pre-wrap text-sm leading-relaxed mb-4">{{ pub.conteudo }}</p>
            
            <div class="mt-6 pt-6 border-t border-black/10 bg-[#F9F9F6]/50 p-4 rounded-sm">
              <h4 class="font-serif text-lg font-bold mb-4 flex items-center gap-2 text-[#0A3161]">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-5 h-5"><path stroke-linecap="round" stroke-linejoin="round" d="M12 20.25c4.97 0 9-3.694 9-8.25s-4.03-8.25-9-8.25S3 7.444 3 11.99c0 2.155 1.05 4.093 2.802 5.378.106.077.164.204.148.33-.081.65-.24 1.341-.564 2.052a.96.96 0 0 0 .918 1.353c1.332 0 2.65-.371 3.82-1.077.08-.049.176-.06.26-.035 1.15.342 2.391.53 3.616.53Z" /></svg>
                Debate Confidencial
              </h4>

              <div v-if="comentarios[pub.id] && comentarios[pub.id].length > 0" class="space-y-3 mb-4">
                <div v-for="comentario in comentarios[pub.id]" :key="comentario.id" class="bg-white p-3 border border-black/5 shadow-sm">
                  <div class="flex justify-between items-start mb-1">
                    <span class="text-[10px] font-black uppercase tracking-widest text-[#0A3161]">{{ comentario.autorUsername }}</span>
                    <span class="text-[9px] text-black/40 font-bold">{{ formatarData(comentario.dataCriacao) }}</span>
                  </div>
                  <p class="font-sans text-sm text-[#1C1C1E]/80">{{ comentario.texto }}</p>
                </div>
              </div>
              <div v-else class="text-xs italic text-black/40 mb-4 px-2">
                Nenhum comentário. Seja o primeiro a adicionar evidências a este dossiê!
              </div>

              <div v-if="isLoggedIn" class="mt-2 flex flex-col sm:flex-row gap-2">
                <input v-model="novoComentario[pub.id]" type="text" placeholder="Escreva aqui (Mínimo 10 caracteres)..." class="flex-grow bg-white border border-black/20 px-3 py-2 text-sm focus:outline-none focus:border-[#CBA052]" @keyup.enter="enviarComentario(pub.id)">
                <button @click="enviarComentario(pub.id)" :disabled="!novoComentario[pub.id] || novoComentario[pub.id].length < 10" class="bg-[#1C1C1E] text-white px-6 py-2 text-[10px] font-bold uppercase tracking-widest hover:bg-[#CBA052] transition-colors disabled:opacity-50">
                  Comentar
                </button>
              </div>
              <div v-else class="text-[10px] uppercase font-bold text-red-600/80 mt-2 px-2 border-l-2 border-red-600">
                Autentique-se no Planeta Diário para participar do debate.
              </div>
            </div>

          </article>
        </div>
      </section>

    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/services/api';

const router = useRouter();
const noticia = ref(null);
const isCriador = ref(false);
const isLoggedIn = ref(false);
const publicacoes = ref([]);
const isPublishing = ref(false);
const form = ref({ titulo: '', conteudo: '' });

const comentarios = ref({});
const novoComentario = ref({});

const toast = reactive({ show: false, message: '', type: 'success' });
const showToast = (msg, type = 'success') => {
  toast.message = msg;
  toast.type = type;
  toast.show = true;
  setTimeout(() => { toast.show = false; }, 4000);
};

const carregarDados = async () => {
  const noticiaSalva = sessionStorage.getItem('noticiaAtual');
  if (!noticiaSalva) {
    router.push('/');
    return;
  }
  noticia.value = JSON.parse(noticiaSalva);

  const token = localStorage.getItem('token');
  if (token) {
    isLoggedIn.value = true;
    try {
      const userRes = await api.get('/api/usuarios/me');
      isCriador.value = userRes.data.tipo === 'CRIADOR_DE_CONTEUDO';
    } catch (e) { 
      console.error("Erro ao verificar tipo de usuário", e);  
      console.warn("Modo leitura ativado"); 
    }
  }

  buscarDossies();
};

const buscarDossies = async () => {
  try {
    const res = await api.get(`/api/publicacoes/noticia?url=${encodeURIComponent(noticia.value.id)}`);
    publicacoes.value = res.data;
    
    publicacoes.value.forEach(pub => {
      buscarComentarios(pub.id);
    });
  } catch (error) {
    console.error("Erro ao buscar dossiês", error);
  }
};

const buscarComentarios = async (pubId) => {
  try {
    const res = await api.get(`/api/comentarios?urlNoticia=${pubId}`);
    comentarios.value[pubId] = res.data;
  } catch (error) {
    console.error(error);
    console.error("Erro ao carregar os comentários", pubId);
  }
};

const enviarComentario = async (pubId) => {
  const texto = novoComentario.value[pubId];
  if (!texto || texto.length < 10) {
    showToast("Seu comentário precisa ter pelo menos 10 caracteres.", "error");
    return;
  }

  try {
    await api.post('/api/comentarios', {
      texto: texto,
      urlNoticia: pubId 
    });
    
    showToast("Comentário adicionado!", "success");
    novoComentario.value[pubId] = '';
    buscarComentarios(pubId); 
  } catch (error) {
    console.error(error);
    showToast("Erro ao submeter o comentário.", "error");
  }
};

const publicarDossie = async () => {
  isPublishing.value = true;
  try {
    await api.post('/api/publicacoes', {
      titulo: form.value.titulo,
      conteudo: form.value.conteudo,
      noticiaUrlRef: noticia.value.id 
    });
    
    showToast("Dossiê publicado! O sistema Pub/Sub transmitiu a notificação.", "success");
    form.value = { titulo: '', conteudo: '' };
    buscarDossies(); 
  } catch (error) {
    console.error(error);
    showToast("Erro ao submeter a publicação.", "error");
  } finally {
    isPublishing.value = false;
  }
};

const formatarData = (dataString) => {
  const data = new Date(dataString);
  return data.toLocaleDateString('pt-BR') + ' às ' + data.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
};

onMounted(carregarDados);
</script>

<style scoped>
.animate-fade-in { animation: fadeIn 0.4s ease-out; }
@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
.slide-down-enter-active, .slide-down-leave-active { transition: all 0.4s ease; }
.slide-down-enter-from, .slide-down-leave-to { opacity: 0; transform: translate(-50%, -20px); }
</style>
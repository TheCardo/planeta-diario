<template>
  <div class="min-h-screen bg-[#F9F9F6] text-[#1C1C1E] pb-12">
    
    <transition name="slide-down">
      <div v-if="toast.show" 
           class="fixed top-8 left-1/2 -translate-x-1/2 z-[100] px-8 py-4 shadow-2xl font-sans font-bold text-sm text-center border-b-4"
           :class="toast.type === 'error' ? 'bg-[#1C1C1E] text-red-400 border-red-600' : 'bg-[#1C1C1E] text-[#CBA052] border-[#CBA052]'">
        {{ toast.message }}
      </div>
    </transition>

    <header class="max-w-5xl mx-auto px-4 pt-6 pb-4 border-b border-[#1C1C1E]/20 mb-8 flex justify-between items-center">
      <button @click="router.push('/')" class="text-xs font-bold uppercase tracking-widest hover:text-[#0A3161] transition-colors flex items-center gap-2">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-4 h-4">
          <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 19.5 3 12m0 0 7.5-7.5M3 12h18" />
        </svg>
        Voltar à Capa
      </button>
      <h1 class="font-serif text-3xl font-black uppercase tracking-tighter">O Planeta Diário</h1>
      <button @click="logout" class="text-[10px] font-bold uppercase text-red-600 hover:underline">Sair das Credenciais</button>
    </header>

    <div v-if="isLoading" class="flex flex-col items-center justify-center h-64 opacity-50">
      <svg class="animate-spin h-10 w-10 text-[#0A3161] mb-4" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"><circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle><path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path></svg>
      <p class="font-serif italic text-lg">Acessando arquivos confidenciais...</p>
    </div>

    <main v-else-if="usuario" class="max-w-5xl mx-auto px-4 animate-fade-in">
      <section class="flex flex-col md:flex-row gap-8 items-start border-b-2 border-[#1C1C1E] pb-12 mb-8 relative">
        <button @click="abrirModal" class="absolute top-0 right-0 text-[10px] font-black uppercase tracking-widest border border-[#1C1C1E] px-4 py-2 hover:bg-[#1C1C1E] hover:text-white transition-colors">
          Editar Arquivo
        </button>

        <div class="w-32 h-32 md:w-48 md:h-48 flex-shrink-0">
          <img :src="usuario?.fotoUrl || 'https://images.unsplash.com/photo-1544502062-f82887f03d1c?q=80&w=400&auto=format&fit=crop'" class="w-full h-full object-cover border-4 border-[#1C1C1E]/10 shadow-lg">
        </div>

        <div class="flex-grow">
          <div class="flex items-center gap-3 mb-1">
            <h2 class="font-serif text-4xl md:text-5xl font-black tracking-tight">{{ usuario?.nome || usuario?.username }}</h2>
            <span v-if="usuario?.tipo === 'CRIADOR_DE_CONTEUDO'" class="bg-[#0A3161] text-white text-[9px] font-bold uppercase tracking-widest px-2 py-1 rounded-sm mt-2">
              {{ usuario?.profissao || 'Jornalista' }}
            </span>
          </div>
          <p class="text-sm font-bold text-black/40 mb-4 uppercase tracking-widest">{{ usuario?.email }}</p>
          <p class="font-sans text-lg text-[#1C1C1E]/80 leading-relaxed mb-6 max-w-2xl whitespace-pre-wrap">
            {{ usuario?.bio || 'Sua Biografia aqui...' }}
          </p>
        </div>
      </section>

      <nav class="flex gap-8 border-b border-[#1C1C1E]/20 mb-8">
        <button v-if="usuario?.tipo === 'CRIADOR_DE_CONTEUDO'" @click="abaAtiva = 'publicacoes'" class="pb-3 text-xs font-black uppercase tracking-widest transition-colors" :class="abaAtiva === 'publicacoes' ? 'border-b-2 border-[#0A3161] text-[#0A3161]' : 'text-black/50 hover:text-black'">
          Meus Dossiês ({{ meusDossies.length }})
        </button>
        <button @click="abaAtiva = 'salvos'" class="pb-3 text-xs font-black uppercase tracking-widest transition-colors" :class="abaAtiva === 'salvos' ? 'border-b-2 border-[#0A3161] text-[#0A3161]' : 'text-black/50 hover:text-black'">
          Acervo Salvo
        </button>
      </nav>
      
      <div v-if="abaAtiva === 'publicacoes'">
        <div v-if="meusDossies.length === 0" class="text-center py-12 text-black/40 italic font-serif text-lg">
          Você ainda não publicou nenhum dossiê.
        </div>
        <div class="space-y-6">
          <article v-for="pub in meusDossies" :key="pub.id" class="border border-[#1C1C1E]/10 p-6 bg-white shadow-sm hover:shadow-md transition-shadow">
            <h3 class="font-serif text-2xl font-bold mb-3">{{ pub.titulo }}</h3>
            <p class="font-sans text-[#1C1C1E]/80 whitespace-pre-wrap text-sm line-clamp-3 mb-4">{{ pub.conteudo }}</p>
            
            <div class="flex flex-col md:flex-row justify-between items-start md:items-center mt-4 border-t border-black/5 pt-4 gap-4">
              <div class="text-[10px] text-black/40 font-bold uppercase tracking-widest">
                Publicado em: {{ formatarData(pub.dataDeCriacao) }}
              </div>
              
              <a :href="pub.noticiaUrlRef" target="_blank" class="text-[10px] font-black uppercase tracking-widest text-[#0A3161] hover:text-[#CBA052] transition-colors flex items-center gap-1">
                Ler Matéria Original
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-3 h-3">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M13.5 4.5 21 12m0 0-7.5 7.5M21 12H3" />
                </svg>
              </a>
            </div>
          </article>
        </div>
      </div>

      <div v-if="abaAtiva === 'salvos'" class="text-center py-12 text-black/40 italic font-serif text-lg">
        Nenhum registro encontrado nestes arquivos no momento.
      </div>
    </main>

    <div v-if="showModal" class="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
      <div class="bg-[#F9F9F6] w-full max-w-md p-8 border-t-8 border-[#0A3161] shadow-2xl">
        <h3 class="font-serif text-2xl font-bold mb-6 italic">Atualizar Credenciais</h3>
        <form @submit.prevent="salvarAlteracoes" class="space-y-4">
          <div>
            <label class="block text-[10px] font-black uppercase mb-1">Nome de Apresentação</label>
            <input v-model="editForm.nome" type="text" placeholder="Seu nome completo" class="w-full bg-transparent border-b border-black py-1 focus:outline-none focus:border-[#0A3161]" required>
          </div>
          <div>
            <label class="block text-[10px] font-black uppercase mb-1">Link da Foto de Perfil</label>
            <input v-model="editForm.fotoUrl" type="text" placeholder="Cole a URL da sua imagem aqui" class="w-full bg-transparent border-b border-black py-1 focus:outline-none focus:border-[#0A3161]">
          </div>
          <div v-if="usuario?.tipo === 'CRIADOR_DE_CONTEUDO'">
            <label class="block text-[10px] font-black uppercase mb-1">Especialidade / Profissão</label>
            <input v-model="editForm.profissao" type="text" class="w-full bg-transparent border-b border-black py-1 focus:outline-none focus:border-[#0A3161]">
          </div>
          <div>
            <label class="block text-[10px] font-black uppercase mb-1">Biografia</label>
            <textarea v-model="editForm.bio" rows="3" class="w-full bg-transparent border border-black/20 p-2 focus:outline-none focus:border-[#0A3161]"></textarea>
          </div>
          <div class="flex gap-4 pt-4">
            <button type="button" @click="showModal = false" class="flex-1 border border-black py-2 text-xs font-bold uppercase hover:bg-black/5 transition-colors">Cancelar</button>
            <button type="submit" class="flex-1 bg-[#0A3161] text-white py-2 text-xs font-bold uppercase shadow-lg hover:bg-[#0A3161]/90 transition-colors">Salvar Arquivo</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/services/api';

const router = useRouter();
const usuario = ref(null);
const isLoading = ref(true);
const abaAtiva = ref('salvos');
const showModal = ref(false);

const meusDossies = ref([]); 
const editForm = ref({ nome: '', bio: '', fotoUrl: '', profissao: '' });

const toast = reactive({ show: false, message: '', type: 'success' });
const showToast = (msg, type = 'success') => {
  toast.message = msg;
  toast.type = type;
  toast.show = true;
  setTimeout(() => { toast.show = false; }, 4000);
};

const logout = () => {
  localStorage.removeItem('token'); 
  router.push('/login');
  setTimeout(() => window.location.reload(), 100); 
};

const carregarPerfil = async () => {
  isLoading.value = true;
  try {
    const response = await api.get('/api/usuarios/me');
    usuario.value = response.data;
    if (usuario.value.tipo === 'CRIADOR_DE_CONTEUDO') {
      abaAtiva.value = 'publicacoes';
      
      try {
        const resDossies = await api.get('/api/publicacoes/meus-dossies');
        meusDossies.value = resDossies.data;
      } catch (e) {
        console.error("Erro ao buscar as publicações do repórter", e);
      }
    }
  } catch (error) {
    console.error("Erro ao carregar perfil. Token inválido. Limpando dados...", error);
    logout();
  } finally {
    isLoading.value = false;
  }
};

const abrirModal = () => {
  editForm.value = { 
    nome: usuario.value?.nome || '', 
    bio: usuario.value?.bio || '', 
    fotoUrl: usuario.value?.fotoUrl || '', 
    profissao: usuario.value?.profissao || '' 
  };
  showModal.value = true;
};

const salvarAlteracoes = async () => {
  try {
    const response = await api.put('/api/usuarios/me', editForm.value);
    usuario.value = response.data; 
    showModal.value = false;
    showToast("Arquivo atualizado com sucesso!", "success");
  } catch (error) {
    console.error(error);
    showToast("Erro ao atualizar credenciais.", "error");
  }
};

const formatarData = (dataString) => {
  const data = new Date(dataString);
  return data.toLocaleDateString('pt-BR') + ' às ' + data.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
};

onMounted(carregarPerfil);
</script>

<style scoped>
.animate-fade-in { 
  animation: fadeIn 0.4s ease-out; 
}

@keyframes fadeIn { 
  from { opacity: 0; } 
  to { opacity: 1; } 
}

.slide-down-enter-active, .slide-down-leave-active {
  transition: all 0.4s ease;
}

.slide-down-enter-from, .slide-down-leave-to {
  opacity: 0;
  transform: translate(-50%, -20px);
}
</style>
<template>
  <div class="min-h-screen bg-[#F9F9F6] text-[#1C1C1E] pb-12">
    <header class="max-w-5xl mx-auto px-4 pt-6 pb-4 border-b border-[#1C1C1E]/20 mb-8 flex justify-between items-center">
      <button @click="router.back()" class="text-xs font-bold uppercase tracking-widest hover:text-[#0A3161] transition-colors flex items-center gap-2">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-4 h-4">
          <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 19.5 3 12m0 0 7.5-7.5M3 12h18" />
        </svg>
        Voltar
      </button>
      <h1 class="font-serif text-3xl font-black uppercase tracking-tighter">O Planeta Diário</h1>
      <div class="w-24"></div> 
    </header>

    <div v-if="isLoading" class="flex justify-center h-64 items-center opacity-50">
      <p class="font-serif italic text-lg">Buscando arquivos do repórter...</p>
    </div>

    <main v-else-if="autor" class="max-w-5xl mx-auto px-4 animate-fade-in">
      <section class="flex flex-col md:flex-row gap-8 items-start border-b-2 border-[#1C1C1E] pb-12 mb-8">
        <div class="w-32 h-32 md:w-48 md:h-48 flex-shrink-0">
          <img :src="autor?.fotoUrl || 'https://images.unsplash.com/photo-1544502062-f82887f03d1c?q=80&w=400&auto=format&fit=crop'" class="w-full h-full object-cover border-4 border-[#1C1C1E]/10 shadow-lg ">
        </div>
        <div class="flex-grow mt-4 md:mt-0">
          <div class="flex items-center gap-3 mb-1">
            <h2 class="font-serif text-4xl md:text-5xl font-black tracking-tight">{{ autor?.nome || autor?.username }}</h2>
            <span v-if="autor?.tipo === 'CRIADOR_DE_CONTEUDO'" class="bg-[#0A3161] text-white text-[9px] font-bold uppercase tracking-widest px-2 py-1 rounded-sm mt-2">
              {{ autor?.profissao || 'Jornalista' }}
            </span>
          </div>
          <p class="text-sm font-bold text-black/40 mb-4 uppercase tracking-widest">Contato Profissional: {{ autor?.email }}</p>
          <p class="font-sans text-lg text-[#1C1C1E]/80 leading-relaxed mb-6 max-w-2xl whitespace-pre-wrap">
            {{ autor?.bio || 'Este repórter ainda não redigiu sua biografia...' }}
          </p>
        </div>
      </section>

      <div class="mt-12 mb-2 border-b-2 border-[#1C1C1E] pb-1">
        <h2 class="font-serif text-2xl font-black uppercase tracking-widest text-[#0A3161]">Dossiês Publicados</h2>
      </div>

      <div v-if="publicacoes.length === 0" class="text-center py-12 text-black/40 italic font-serif text-lg">
        Nenhum dossiê publicado ainda.
      </div>

      <div class="space-y-6 mt-6">
        <article v-for="pub in publicacoes" :key="pub.id" class="border border-[#1C1C1E]/10 p-6 bg-white shadow-sm hover:shadow-md transition-shadow">
          <h3 class="font-serif text-2xl font-bold mb-3">{{ pub.titulo }}</h3>
          <p class="font-sans text-[#1C1C1E]/80 whitespace-pre-wrap text-sm line-clamp-3 mb-4">{{ pub.conteudo }}</p>
          <div class="flex justify-between items-center mt-4 border-t border-black/5 pt-4">
            <div class="text-[10px] text-black/40 font-bold uppercase tracking-widest">
              Publicado em: {{ formatarData(pub.dataDeCriacao) }}
            </div>
            <a :href="pub.noticiaUrlRef" target="_blank" class="text-[10px] font-black uppercase tracking-widest text-[#0A3161] hover:text-[#CBA052]">
              Ler Matéria Original →
            </a>
          </div>
        </article>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import api from '@/services/api';

const route = useRoute();
const router = useRouter();
const autor = ref(null);
const publicacoes = ref([]);
const isLoading = ref(true);

const carregarAutor = async () => {
  try {
    const username = route.params.username;
    const resAutor = await api.get(`/api/usuarios/${username}`);
    autor.value = resAutor.data;

    if (autor.value.tipo === 'CRIADOR_DE_CONTEUDO') {
      const resPubs = await api.get(`/api/publicacoes/autor/${username}`);
      publicacoes.value = resPubs.data;
    }
  } catch (error) {
    console.error("Erro ao carregar o autor", error);
  } finally {
    isLoading.value = false;
  }
};

const formatarData = (dataString) => {
  const data = new Date(dataString);
  return data.toLocaleDateString('pt-BR') + ' às ' + data.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
};

onMounted(carregarAutor);
</script>
<template>
  <div class="min-h-screen bg-[#F9F9F6] text-[#1C1C1E] pb-12">
    
    <header class="max-w-7xl mx-auto px-4 pt-6">
      
      <div class="flex justify-between items-end border-b-2 border-[#1C1C1E] pb-2 mb-2">
        <div class="text-[10px] font-bold uppercase tracking-widest hidden md:block w-1/4">
          {{ currentDate }}
        </div>
        
        <h1 class="font-serif text-6xl md:text-8xl font-black uppercase tracking-tighter text-center flex-grow">
          O Planeta Diário
        </h1>
        
        <div class="text-[10px] font-bold uppercase tracking-widest hidden md:flex w-1/4 justify-end items-center gap-4">
          <span>Edição Digital</span>
          
          <button @click="irParaPerfil" class="hover:text-[#0A3161] transition-colors" title="Acessar Meu Perfil">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-5 h-5">
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
            </svg>
          </button>
        </div>
      </div>
      
      <nav class="border-b border-[#1C1C1E]/30 pb-3 mb-8 flex flex-col md:flex-row justify-between items-center gap-4">
        
        <ul class="flex flex-wrap justify-center gap-x-6 gap-y-2 text-[11px] font-black uppercase tracking-widest flex-grow">
          <li v-for="categoria in categorias" :key="categoria">
            <button 
              @click="categoriaAtiva = categoria"
              class="transition-colors hover:text-[#0A3161] pb-1"
              :class="categoriaAtiva === categoria ? 'text-[#0A3161] border-b-2 border-[#0A3161]' : 'text-[#1C1C1E]/70'"
            >
              {{ categoria }}
            </button>
          </li>
        </ul>

        <div class="flex items-center gap-2 text-[10px] font-black uppercase tracking-widest pl-4 md:border-l border-[#1C1C1E]/30">
          <label for="countryFilter" class="text-black/60">País:</label>
          <select 
            id="countryFilter" 
            v-model="paisAtivo" 
            class="bg-transparent border-none outline-none cursor-pointer hover:text-[#0A3161] transition-colors font-bold uppercase tracking-widest"
          >
            <option value="br">Brasil</option>
            <option value="us">Estados Unidos</option>
            <option value="pt">Portugal</option>
            <option value="gb">Reino Unido</option>
            <option value="ar">Argentina</option>
          </select>
        </div>

      </nav>
    </header>

    <main class="max-w-7xl mx-auto px-4 grid grid-cols-1 lg:grid-cols-12 gap-8">
      
      <section class="lg:col-span-8 border-r-0 lg:border-r border-[#1C1C1E]/20 lg:pr-8">
        <div class="mb-2 border-b border-black/10 pb-1">
          <h2 class="text-xs font-black uppercase tracking-widest text-[#0A3161]">Manchete Principal</h2>
        </div>
        
        <article class="group cursor-pointer">
          <h1 class="font-serif text-4xl md:text-5xl font-bold leading-none mb-4 group-hover:text-[#0A3161] transition-colors">
            {{ topHeadline.titulo }}
          </h1>
          <div class="flex flex-col md:flex-row gap-6 items-start">
            <div class="w-full md:w-2/3 order-2 md:order-1">
              <p class="font-sans text-lg text-[#1C1C1E]/80 mb-4 leading-relaxed">
                {{ topHeadline.resumo }}
              </p>
              <div class="text-[10px] font-bold uppercase tracking-wider text-black/50">
                Por {{ topHeadline.autor }} • {{ topHeadline.tempoAtras }}
              </div>
            </div>
            <div class="w-full md:w-1/3 order-1 md:order-2">
              <img :src="topHeadline.imagem" class="w-full h-auto object-cover border border-black/10 grayscale group-hover:grayscale-0 transition-all duration-500" alt="Imagem da manchete">
            </div>
          </div>
        </article>

        <hr class="my-8 border-t border-[#1C1C1E]/20">

        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
          <article v-for="noticia in noticiasSecundarias" :key="noticia.id" class="group cursor-pointer border-b md:border-b-0 border-[#1C1C1E]/10 pb-6 md:pb-0">
            <img :src="noticia.imagem" class="w-full h-48 object-cover mb-3 border border-black/10 grayscale group-hover:grayscale-0 transition-all" alt="">
            <h3 class="font-serif text-2xl font-bold leading-tight mb-2 group-hover:text-[#0A3161] transition-colors">
              {{ noticia.titulo }}
            </h3>
            <p class="font-sans text-sm text-[#1C1C1E]/70 mb-2 line-clamp-3">
              {{ noticia.resumo }}
            </p>
            <div class="text-[10px] font-bold uppercase tracking-wider text-[#CBA052]">
              {{ noticia.categoria }}
            </div>
          </article>
        </div>
      </section>

      <aside class="lg:col-span-4">
        <div class="mb-2 border-b-2 border-[#1C1C1E] pb-1">
          <h2 class="text-xs font-black uppercase tracking-widest text-[#1C1C1E]">Últimas Atualizações</h2>
        </div>
        
        <div class="flex flex-col gap-4 mt-4">
          <article v-for="noticia in ultimasAtualizacoes" :key="noticia.id" class="cursor-pointer group border-b border-[#1C1C1E]/10 pb-4 last:border-0">
            <span class="text-[10px] font-bold text-black/40 mb-1 block">{{ noticia.tempoAtras }}</span>
            <h4 class="font-serif text-lg font-bold leading-tight group-hover:text-[#0A3161] transition-colors">
              {{ noticia.titulo }}
            </h4>
          </article>
        </div>

        <div class="mt-8 bg-[#E5E5E1] p-6 border border-[#1C1C1E]/20 text-center">
          <h3 class="font-serif text-xl font-bold mb-2">Acervo Confidencial</h3>
          <p class="text-sm text-black/70 mb-4">Acesse matérias exclusivas e participe dos debates nos comentários.</p>
          <button @click="irParaLogin" class="bg-[#1C1C1E] text-white px-6 py-2 text-xs font-bold uppercase tracking-widest hover:bg-[#0A3161] transition-colors w-full cursor-pointer">
            Fazer Login
          </button>
        </div>
      </aside>

    </main>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/services/api'; 

const router = useRouter();

const categorias = ['Geral', 'Mundo', 'Nação', 'Negócios', 'Tecnologia', 'Entretenimento', 'Esportes', 'Ciência', 'Saúde'];
const categoriaAtiva = ref('Geral');
const paisAtivo = ref('br'); 

const categoriaMap = {
  'Geral': 'general', 'Mundo': 'world', 'Nação': 'nation', 
  'Negócios': 'business', 'Tecnologia': 'technology', 
  'Entretenimento': 'entertainment', 'Esportes': 'sports', 
  'Ciência': 'science', 'Saúde': 'health'
};

const currentDate = computed(() => {
  const options = { weekday: 'long', day: '2-digit', month: 'long', year: 'numeric' };
  return new Date().toLocaleDateString('pt-BR', options).toUpperCase();
});

const isLoading = ref(true);
const topHeadline = ref({});
const noticiasSecundarias = ref([]);
const ultimasAtualizacoes = ref([]);

const buscarNoticias = async () => {
  isLoading.value = true;
  try {
    const categoriaParaBackend = categoriaMap[categoriaAtiva.value];
    const response = await api.get(`/api/news/top/${categoriaParaBackend}`);
    const artigosBack = response.data.articles || [];

    if (artigosBack.length > 0) {
      topHeadline.value = {
        titulo: artigosBack[0].title, 
        resumo: artigosBack[0].description, 
        autor: artigosBack[0].source ? artigosBack[0].source.name : 'Redação', 
        tempoAtras: formatarData(artigosBack[0].publishedAt),
        imagem: artigosBack[0].image || 'https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=800&q=80'
      };

      noticiasSecundarias.value = artigosBack.slice(1, 5).map(art => ({
        id: art.url, 
        titulo: art.title,
        resumo: art.description,
        categoria: categoriaAtiva.value,
        imagem: art.image || 'https://images.unsplash.com/photo-1546422904-90eab23c3d4e?w=600&q=80'
      }));

      ultimasAtualizacoes.value = artigosBack.slice(5, 10).map(art => ({
        id: art.url,
        titulo: art.title,
        tempoAtras: formatarData(art.publishedAt)
      }));
    }

  } catch (error) {
    console.error("Erro na API (provável Limite 429). Ativando o Modo Offline de emergência:", error);
    
    topHeadline.value = {
      titulo: '[MODO OFFLINE] GNews API Atinge Limite de Requisições',
      resumo: 'O Daily Planet entrou em modo de contingência. Os repórteres consumiram toda a cota de requisições gratuitas da API. Enquanto aguardamos o reset do servidor, este cache local manterá a sua tela funcionando para fins de desenvolvimento do layout.',
      autor: 'Lois Lane (Backup)',
      tempoAtras: 'Agora mesmo',
      imagem: 'https://images.unsplash.com/photo-1504711434969-e33886168f5c?q=80&w=800&auto=format&fit=crop'
    };

    noticiasSecundarias.value = [
      {
        id: 'mock1',
        titulo: 'Como lidar com Erros 429 no Frontend',
        resumo: 'Aprenda a criar fallbacks e placeholders para manter a retenção do usuário quando serviços de terceiros falham.',
        categoria: categoriaAtiva.value,
        imagem: 'https://images.unsplash.com/photo-1555066931-4365d14bab8c?q=80&w=600&auto=format&fit=crop'
      },
      {
        id: 'mock2',
        titulo: 'A Importância da Resiliência de Software',
        resumo: 'Sistemas modernos precisam estar preparados para falhas de rede. O uso de Caches e Circuit Breakers é essencial.',
        categoria: 'Engenharia',
        imagem: 'https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?q=80&w=600&auto=format&fit=crop'
      }
    ];

    ultimasAtualizacoes.value = [
      { id: 'u1', tempoAtras: 'Sistema', titulo: 'API GNews temporariamente indisponível.' },
      { id: 'u2', tempoAtras: 'Sistema', titulo: 'Carregando dados a partir do cache local.' },
      { id: 'u3', tempoAtras: 'Dica', titulo: 'Gere uma nova API Key se quiser dados reais agora.' }
    ];

  } finally {
    isLoading.value = false;
  }
};

const formatarData = (dataString) => {
  if (!dataString) return 'Recentemente';
  const data = new Date(dataString);
  return data.toLocaleDateString('pt-BR') + ' às ' + data.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
};

onMounted(() => {
  buscarNoticias();
});

watch(categoriaAtiva, () => {
  buscarNoticias();
});

const irParaPerfil = () => {
  router.push('/profile');
};

const irParaLogin = () => {
  router.push('/login');
};
</script>
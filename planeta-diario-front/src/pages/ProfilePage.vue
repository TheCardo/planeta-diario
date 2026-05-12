<template>
  <div class="min-h-screen bg-[#F9F9F6] text-[#1C1C1E] pb-12">
    
    <header class="max-w-5xl mx-auto px-4 pt-6 pb-4 border-b border-[#1C1C1E]/20 mb-8 flex justify-between items-center">
      <button @click="voltarParaHome" class="text-xs font-bold uppercase tracking-widest hover:text-[#0A3161] transition-colors flex items-center gap-2">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-4 h-4">
          <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 19.5 3 12m0 0 7.5-7.5M3 12h18" />
        </svg>
        Voltar à Capa
      </button>
      <h1 class="font-serif text-3xl font-black uppercase tracking-tighter">
        O Planeta Diário
      </h1>
      <div class="w-24"></div> 
    </header>

    <main class="max-w-5xl mx-auto px-4">
      
      <section class="flex flex-col md:flex-row gap-8 items-start border-b-2 border-[#1C1C1E] pb-12 mb-8 relative">
        
        <button class="absolute top-0 right-0 text-[10px] font-black uppercase tracking-widest border border-[#1C1C1E] px-4 py-2 hover:bg-[#1C1C1E] hover:text-white transition-colors">
          Editar Arquivo
        </button>

        <div class="w-32 h-32 md:w-48 md:h-48 flex-shrink-0">
          <img :src="usuario.foto" alt="Foto de Perfil" class="w-full h-full object-cover border-4 border-[#1C1C1E]/10 shadow-lg">
        </div>

        <div class="flex-grow">
          <div class="flex items-center gap-3 mb-1">
            <h2 class="font-serif text-4xl md:text-5xl font-black tracking-tight">{{ usuario.nome }}</h2>
            
            <span v-if="usuario.tipo === 'CRIADOR'" class="bg-[#0A3161] text-white text-[9px] font-bold uppercase tracking-widest px-2 py-1 rounded-sm mt-2">
              {{ usuario.profissao }}
            </span>
          </div>
          
          <p class="text-sm font-bold text-black/40 mb-4 uppercase tracking-widest">@{{ usuario.username }}</p>
          
          <p class="font-sans text-lg text-[#1C1C1E]/80 leading-relaxed mb-6 max-w-2xl">
            {{ usuario.bio }}
          </p>

          <div v-if="usuario.tipo === 'CRIADOR' && usuario.links.length > 0" class="flex gap-4">
            <a v-for="link in usuario.links" :key="link.url" :href="link.url" target="_blank" class="text-xs font-bold uppercase tracking-widest text-[#CBA052] hover:text-[#0A3161] transition-colors underline underline-offset-4">
              {{ link.nome }}
            </a>
          </div>
        </div>
      </section>

      <section>
        <nav class="flex gap-8 border-b border-[#1C1C1E]/20 mb-8">
          <button 
            v-if="usuario.tipo === 'CRIADOR'"
            @click="abaAtiva = 'publicacoes'"
            class="pb-3 text-xs font-black uppercase tracking-widest transition-colors"
            :class="abaAtiva === 'publicacoes' ? 'border-b-2 border-[#0A3161] text-[#0A3161]' : 'text-black/50 hover:text-black'"
          >
            Dossiês Publicados
          </button>
          
          <button 
            @click="abaAtiva = 'salvos'"
            class="pb-3 text-xs font-black uppercase tracking-widest transition-colors"
            :class="abaAtiva === 'salvos' ? 'border-b-2 border-[#0A3161] text-[#0A3161]' : 'text-black/50 hover:text-black'"
          >
            Acervo Salvo
          </button>
        </nav>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          
          <article v-for="artigo in artigosExibidos" :key="artigo.id" class="group cursor-pointer border border-[#1C1C1E]/10 p-4 hover:shadow-lg transition-all bg-white">
            <img :src="artigo.imagem" class="w-full h-40 object-cover mb-4 grayscale group-hover:grayscale-0 transition-all" alt="">
            <div class="text-[9px] font-bold uppercase tracking-widest text-[#CBA052] mb-2">
              {{ artigo.categoria }} • {{ artigo.data }}
            </div>
            <h3 class="font-serif text-xl font-bold leading-tight mb-2 group-hover:text-[#0A3161] transition-colors line-clamp-2">
              {{ artigo.titulo }}
            </h3>
            <p class="font-sans text-xs text-[#1C1C1E]/70 line-clamp-3">
              {{ artigo.resumo }}
            </p>
          </article>

        </div>
        
        <div v-if="artigosExibidos.length === 0" class="text-center py-12 text-black/40 italic font-serif text-lg">
          Nenhum registro encontrado nestes arquivos.
        </div>

      </section>

    </main>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const voltarParaHome = () => {
  router.push('/');
};

const abaAtiva = ref('publicacoes');

const usuario = ref({
  nome: 'Clark Kent',
  username: 'ckent_daily',
  tipo: 'CRIADOR', 
  profissao: 'Jornalista', 
  foto: 'https://preview.redd.it/clark-kent-by-jorge-jim%C3%A9nez-v0-y4dp5pwb9yl01.jpg?auto=webp&s=e1e5f1dcbdc885ef3fc44a20e329e3f5d768fbd7',
  bio: 'Repórter investigativo Sênior no Planeta Diário. Ganhador do Prêmio Pulitzer. Foco em expor corrupção corporativa e cobrir os eventos extraordinários de Metropolis.',
  links: [
    { nome: 'Twitter (X)', url: '#' },
    { nome: 'Portfólio', url: '#' }
  ]
});

const publicacoes = ref([
  {
    id: 1,
    titulo: 'LexCorp sob investigação do comitê de segurança de Metropolis',
    resumo: 'Documentos vazados indicam desvio de verbas em projetos de infraestrutura da prefeitura.',
    categoria: 'Nação',
    data: '10 MAI 2026',
    imagem: 'https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?q=80&w=600&auto=format&fit=crop'
  },
  {
    id: 2,
    titulo: 'O Homem de Aço: Uma entrevista exclusiva',
    resumo: 'Após o incidente na ponte, consegui algumas palavras exclusivas com o herói de Metropolis.',
    categoria: 'Entrevista',
    data: '08 MAI 2026',
    imagem: 'https://images.unsplash.com/photo-1618519764620-7403abdbdfe9?q=80&w=600&auto=format&fit=crop'
  }
]);

const salvos = ref([
  {
    id: 3,
    titulo: 'Nova feira de tecnologia atrai olhares do mundo todo',
    resumo: 'Inovações em IA e robótica são o destaque do evento anual de Ciências de Metropolis.',
    categoria: 'Tecnologia',
    data: '11 MAI 2026',
    imagem: 'https://images.unsplash.com/photo-1518770660439-4636190af475?q=80&w=600&auto=format&fit=crop'
  }
]);

const artigosExibidos = computed(() => {
  return abaAtiva.value === 'publicacoes' ? publicacoes.value : salvos.value;
});
</script>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;  
  overflow: hidden;
}
.line-clamp-3 {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;  
  overflow: hidden;
}
</style>
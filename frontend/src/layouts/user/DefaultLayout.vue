<template>
    <div class=" bg-gray-50">
        <Header />
        <slot></slot>
        <Footer />
    </div>
</template>
<script setup>
import Header from '@/components/header/Header.vue';
import Footer from '@/components/footer/Footer.vue';



</script>
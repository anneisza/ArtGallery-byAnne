// Mengambil fungsi hook dari library CDN global
const { motion, useScroll, useTransform } = Motion;
const { useState, useEffect, useRef } = React;

function ContainerScroll({ titleComponent, children }) {
    const containerRef = useRef(null);
    const { scrollYProgress } = useScroll({
        target: containerRef,
    });
    const [isMobile, setIsMobile] = useState(false);

    useEffect(() => {
        const checkMobile = () => {
            setIsMobile(window.innerWidth <= 768);
        };
        checkMobile();
        window.addEventListener("resize", checkMobile);
        return () => window.removeEventListener("resize", checkMobile);
    }, []);

    const scaleDimensions = () => {
        return isMobile ? [0.7, 0.9] : [1.05, 1];
    };

    const rotate = useTransform(scrollYProgress, [0, 1], [20, 0]);
    const scale = useTransform(scrollYProgress, [0, 1], scaleDimensions());
    const translate = useTransform(scrollYProgress, [0, 1], [0, -100]);

    /* SESUAI TEMA MAMBA: Mengubah pendaran shadow biru menjadi pendaran putih/silver galeri yang eksklusif */
    const dynamicShadow = useTransform(
        scrollYProgress,
        [0, 1],
        [
            "0 20px 40px rgba(0,0,0,0.9), 0 0 20px rgba(255, 255, 255, 0.02)", // Saat di atas (hampir menyatu gelap)
            "0 40px 80px rgba(0,0,0,0.95), 0 0 40px rgba(255, 255, 255, 0.08), 0 0 20px rgba(255, 255, 255, 0.04)" // Saat scroll ke bawah (pendaran eksklusif tipis)
        ]
    );

    return (
         <div className="h-[50rem] md:h-[65rem] w-full flex flex-col items-center justify-center relative p-2 md:p-20" ref={containerRef}>
             <div className="py-10 md:py-20 w-full flex flex-col items-center relative" style={{ perspective: "1000px" }}>

                 <div
                     style={{ transform: `translateY(${translate}px)`, textAlign: 'center' }}
                     className="w-full max-w-5xl mx-auto text-center px-4"
                 >
                     {titleComponent}
                 </div>

                 {/* Card Utama Animasi - Disesuaikan dengan border halus putih & bg hitam mamba */}
                 <motion.div
                     style={{
                         rotateX: rotate,
                         scale,
                         boxShadow: dynamicShadow,
                     }}
                     className="max-w-5xl -mt-12 mx-auto h-[25rem] md:h-[35rem] w-full border border-white/10 bg-[#121212] rounded-[24px]"
                 >
                     <div className="h-full w-full overflow-hidden rounded-[22px] relative">
                         {children}
                     </div>
                 </motion.div>

             </div>
         </div>
    );
}

function ScrollApp() {
    return (
        <ContainerScroll
            titleComponent={
                <div>
                    <span className="text-xs font-medium tracking-[3px] uppercase text-zinc-500 block mb-2">VIRTUAL SELECTION</span>
                    <h2 className="text-3xl md:text-6xl font-light tracking-tight text-white mb-4" style={{ fontFamily: "'Playfair Display', serif" }}>
                        A New Dimension of <br />
                        {/* Mengubah gradasi biru-neon menjadi gradasi putih-abu mewah khas cewek mamba */}
                        <span className="bg-clip-text text-transparent bg-gradient-to-r from-white via-zinc-200 to-zinc-400 font-normal italic">
                           Digital Exhibition
                        </span>
                    </h2>
                </div>
            }
        >
            {/* AREA DALAM CARD - Background dibersihkan total menjadi hitam pekat galeri */}
            <div className="w-full h-full relative flex items-center justify-center bg-[#0d0d0d] overflow-hidden">

                {/* Lampu ambient diubah dari biru menjadi putih redup untuk fokus sorotan seni */}
                <div className="absolute w-96 h-96 bg-white/[0.03] rounded-full blur-[130px] top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 pointer-events-none"></div>

                {/* Gradasi overlay gelap */}
                <div className="absolute inset-0 bg-gradient-to-t from-black/40 via-transparent to-black/10 z-20 pointer-events-none"></div>

                {/* GAMBAR UTAMA */}
                <img
                    src="./photos/download.jfif"
                    alt="Pameran Seni"
                    className="w-full h-full object-cover relative z-10 transition-transform duration-500 ease-out hover:scale-[1.03]"
                />
            </div>
        </ContainerScroll>
    );
}

// Render komponen ke jangkar di halaman home.html
const root = ReactDOM.createRoot(document.getElementById('container-scroll-root'));
root.render(<ScrollApp />);
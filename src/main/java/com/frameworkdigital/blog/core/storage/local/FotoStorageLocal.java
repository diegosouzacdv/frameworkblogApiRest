package com.frameworkdigital.blog.core.storage.local;



import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.frameworkdigital.blog.core.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;


@Profile("local")
@Component
public class FotoStorageLocal implements FotoStorage {


		private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
		
		private Path local;
		private Path localTemporario;

		
		
		
		
		public Path getLocal() {
			
			this.local = getDefault().getPath(defaultDirectory());
			
			return local;
		}


		public void setLocal(Path local) {
			this.local = local;
		}


		public Path getLocalTemporario() {
			
			return localTemporario;
		}


		public void setLocalTemporario(Path localTemporario) {
			this.localTemporario = localTemporario;
		}


		private static String defaultDirectory()
		{
		    String OS = System.getProperty("os.name").toUpperCase();
		    
		    String nome = "";
		    
		    if (OS.contains("WIN"))
		    	nome = System.getenv("APPDATA");
		    else if (OS.contains("MAC"))
		    	nome = System.getProperty("user.home") + "/Library/Application "
		                + "Support";
		    else if (OS.contains("NUX"))
		    	nome = System.getProperty("user.home");
		   
		    return nome;
		}
		
		
		public FotoStorageLocal() {
			this(getDefault().getPath(defaultDirectory()));
		}
		
		public FotoStorageLocal(Path path) {
			this.local = path;
			
		}
		
		

		@Override
		public String salvarTemporariamente(MultipartFile files,String diretorio) { 
			
			
			criarPastas(diretorio);
			
			String novoNome = null;
			if (files != null) {
				MultipartFile arquivo = files;
				novoNome = renomearArquivo(arquivo.getOriginalFilename());
				try {
					arquivo.transferTo(new File(defaultDirectory()+getDefault().getSeparator()+diretorio+getDefault().getSeparator()+"temp"+getDefault().getSeparator()+ novoNome));
				} catch (IOException e) {
					throw new RuntimeException("Erro salvando a foto na pasta temporária", e); 
				}
			}
			salvar(novoNome,diretorio);
			return novoNome;
		}
		
		@Override
		public byte[] recuperarFotoTemporaria(String nome) {
			try {
				return Files.readAllBytes(this.local.resolve(nome));
			} catch (IOException e) {
				throw new RuntimeException("Erro lendo a foto temporária", e);
			}
		}
		
		@Override
		public void salvar(String foto,String diretorio) {
			
			this.local = getDefault().getPath(defaultDirectory()+getDefault().getSeparator()+File.separator+diretorio);
			this.localTemporario = getDefault().getPath(defaultDirectory()+getDefault().getSeparator()+diretorio+getDefault().getSeparator()+"temp");
			
			try {
				Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
			} catch (IOException e) {
				throw new RuntimeException("Erro movendo a foto para destino final", e);
			}
			
			try {
				Thumbnails.of(this.local.resolve(foto).toString()).size(200, 200).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
			} catch (IOException e) {
				throw new RuntimeException("Erro gerando thumbnail", e);
			}
		}
		
		@Override
		public byte[] recuperar(String nome,String diretorio) {
			System.err.println(diretorio);
			this.local = getDefault().getPath(defaultDirectory()+File.separator+diretorio);
			
			try {
				return Files.readAllBytes(this.local.resolve(nome));
			} catch (IOException e) {
				throw new RuntimeException("Erro lendo a foto", e);
			}
		}
		
		
		
		private void criarPastas(String local) {
			try {
				
				
				Files.createDirectories(getDefault().getPath(defaultDirectory(),local));
				//this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
				Files.createDirectories(getDefault().getPath(defaultDirectory()+getDefault().getSeparator()+local, "temp"));
				
				if (logger.isDebugEnabled()) {
					logger.debug("Pastas criadas para salvar fotos.");
					logger.debug("Pasta default: " + this.local.toAbsolutePath());
					logger.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
				}
			} catch (IOException e) {
				throw new RuntimeException("Erro criando pasta para salvar foto", e);
			}
		}
		
		private String renomearArquivo(String nomeOriginal) {
			
			String tem[] = nomeOriginal.split("\\."); 
			
			String novoNome = UUID.randomUUID().toString() + "." +tem[1];
			
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Nome original: %s, novo nome: %s", nomeOriginal, novoNome));
			}
			
			return novoNome;
			
		}


		@Override
		public int remover(String nome, String diretorio) {
				System.err.println(diretorio);
			this.local = getDefault().getPath(defaultDirectory()+File.separator+diretorio);
			
			try {
				Files.deleteIfExists(this.local.resolve(nome));
				Files.deleteIfExists(this.local.resolve("thumbnail."+nome));
				return 1;
			} catch (IOException e) {
				return 0;
			}
		}
		
		

	
}

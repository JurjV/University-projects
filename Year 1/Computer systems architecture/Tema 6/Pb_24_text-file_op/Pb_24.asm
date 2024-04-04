;   A file name and a text (defined in data segment) are given. The text contains lowercase letters, uppercase letters, digits and special characters. 
;   Replace all digits from the text with character 'C'. Create a file with the given name and write the generated text to file.

bits 32

global start

; declare external functions needed by our program
extern exit, fopen, fprintf, fclose, printf, fread, fwrite, remove, rename
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fread msvcrt.dll
import fwrite msvcrt.dll
import remove msvcrt.dll
import rename msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data

    
    fileName db "a.txt", 0
    text db "m99ab1=[3*7"
    len equ $-text
    res_text times len+1 db 0
    file_descriptor dd -1
    accessModeWrite db "w", 0
    element db 0
    
; our code starts here
segment code use32 class=code
start:  

    mov ecx,len
    mov esi,text
    mov edi,res_text
    cld
    repeta:
        mov dl,0  ; condition counter
        lodsb
        cmp al,30h
        jb sari1
            inc dl ; condition met dl = 1
        
        sari1:
        cmp al,39h
        ja sari2
            inc dl ; conditioin met dl = 2
        
        sari2:
        cmp dl,2   ; dl = 2 if the ascii code of the character is between 30 and 39 inclusively
        jne sari3  ; if dl == 2 change the current byte to ascii code of C
            mov al,"C"
        
        sari3:
        stosb ; store the current byte in the resulting text
        loop repeta
        
        
    ; Opening the write file - a.txt
    push dword accessModeWrite
    push dword fileName
    call [fopen]  
    add esp, 4 * 2
    
    mov [file_descriptor], eax
    cmp eax,0     ; checking if the file creation is valid
    je IncorectCreation
    
    push dword res_text   ; writting the resulting text in the file
    push dword [file_descriptor]
    call [fprintf]
    add esp, 4*2
    
    push dword [file_descriptor]
    call [fclose]    ; closing the file
    add esp, 4
    
    IncorectCreation:
    push dword 0      
    call [exit]
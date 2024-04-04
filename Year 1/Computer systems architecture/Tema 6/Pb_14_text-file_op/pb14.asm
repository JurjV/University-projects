;A file name and a text (defined in the data segment) are given. The text contains lowercase letters, uppercase letters, digits and special characters. Transform all the uppercase letters from the given text in lowercase. Create a file with the given name and write the generated text to file.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fprintf,fopen,fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fprintf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data

    file_name db "file.txt",0
    text db "BbcABC123[]B",0
    len equ $-text
    res_text times len db 0
    write_mode db "w+",0
    write_format db "%s",0
    file_descriptor dd 0

; our code starts here
segment code use32 class=code
    start:
        mov ecx,len
        mov esi,text
        mov edi,res_text
        cld
        Repeta:
        push ecx
            lodsb
            cmp al,"A"
            jb Not_a_letter
            cmp al,"Z"
            ja Not_a_letter
            add al,"a"-"A"
            Not_a_letter:
            stosb
            pop ecx
            loop Repeta
            
       push dword write_mode
       push dword file_name
       call [fopen]
       add esp,4*2
       
       cmp eax,0
       je Incorrect
       mov [file_descriptor],eax
       
       push dword res_text
       push dword write_format
       push dword [file_descriptor]
       call [fprintf]
       add esp,4*3
       
       push dword [file_descriptor]
       call [fclose]    ; closing the file
       add esp, 4
       
       Incorrect:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

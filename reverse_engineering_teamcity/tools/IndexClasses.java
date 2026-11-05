import org.objectweb.asm.*;
import java.io.*;
import java.util.*;
public class IndexClasses {
 static String q(String s){if(s==null)return "null";var b=new StringBuilder("\"");for(char c:s.toCharArray()){if(c=='"'||c=='\\')b.append('\\').append(c);else if(c<32)b.append(String.format("\\u%04x",(int)c));else b.append(c);}return b.append('"').toString();}
 static String json(Object v){if(v==null)return "null";if(v instanceof String)return q((String)v);if(v instanceof Number||v instanceof Boolean)return v.toString();if(v instanceof Map){var a=new ArrayList<String>();((Map<?,?>)v).forEach((k,x)->a.add(q(k.toString())+":"+json(x)));return "{"+String.join(",",a)+"}";}if(v instanceof Collection){var a=new ArrayList<String>();for(Object x:(Collection<?>)v)a.add(json(x));return "["+String.join(",",a)+"]";}return q(v.toString());}
 static Map<String,Object> m(Object... kv){var x=new LinkedHashMap<String,Object>();for(int i=0;i<kv.length;i+=2)x.put(kv[i].toString(),kv[i+1]);return x;}
 public static void main(String[] args)throws Exception{
 var in=new DataInputStream(new BufferedInputStream(System.in));var out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out,java.nio.charset.StandardCharsets.UTF_8)));
 while(true){String location;try{int n=in.readInt();location=new String(in.readNBytes(n),java.nio.charset.StandardCharsets.UTF_8);}catch(EOFException e){break;}int n=in.readInt();byte[] bytes=in.readNBytes(n);
 try{var r=m("location",location,"sha256",HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(bytes)));var methods=new ArrayList<Object>();var fields=new ArrayList<Object>();var annotations=new ArrayList<Object>();r.put("methods",methods);r.put("fields",fields);r.put("annotations",annotations);
 new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM9){
 public void visit(int version,int access,String name,String sig,String sup,String[] interfaces){r.putAll(m("name",name,"version",version,"access",access,"signature",sig,"super",sup,"interfaces",Arrays.asList(interfaces)));}
 public void visitSource(String source,String debug){r.put("source",source);r.put("source_debug_present",debug!=null);}
 public AnnotationVisitor visitAnnotation(String desc,boolean visible){annotations.add(desc);return null;}
 public FieldVisitor visitField(int access,String name,String desc,String sig,Object value){fields.add(m("name",name,"descriptor",desc,"access",access,"signature",sig,"constant",value==null?null:value.toString()));return null;}
 public MethodVisitor visitMethod(int access,String name,String desc,String sig,String[] exceptions){var calls=new ArrayList<Object>();var state=new ArrayList<Object>();var locals=new ArrayList<Object>();var anns=new ArrayList<Object>();var branches=new ArrayList<Object>();var handlers=new ArrayList<Object>();var method=m("name",name,"descriptor",desc,"access",access,"signature",sig,"exceptions",exceptions==null?List.of():Arrays.asList(exceptions),"calls",calls,"field_accesses",state,"locals",locals,"annotations",anns,"branches",branches,"handlers",handlers);methods.add(method); if(!r.get("name").toString().matches("(jetbrains/.*|com/jetbrains/.*|org/jetbrains/teamcity/.*)")){method.put("body_indexed",false);return null;} method.put("body_indexed",true);
 return new MethodVisitor(Opcodes.ASM9){int ordinal=0,line=-1; IdentityHashMap<Label,Integer> labels=new IdentityHashMap<>();int label(Label l){return labels.computeIfAbsent(l,k->labels.size());}
 public AnnotationVisitor visitAnnotation(String d,boolean v){anns.add(d);return null;}
 public void visitCode(){method.put("has_code",true);}
 public void visitLineNumber(int l,Label start){line=l;}
 public void visitLabel(Label l){branches.add(m("label",label(l),"instruction",ordinal));}
 public void visitInsn(int op){ordinal++;}
 public void visitIntInsn(int op,int value){ordinal++;}
 public void visitVarInsn(int op,int v){ordinal++;}
 public void visitTypeInsn(int op,String type){ordinal++;}
 public void visitLdcInsn(Object v){ordinal++;}
 public void visitIincInsn(int v,int i){ordinal++;}
 public void visitMultiANewArrayInsn(String d,int dim){ordinal++;}
 public void visitMethodInsn(int op,String owner,String n,String d,boolean itf){calls.add(m("instruction",ordinal++,"line",line,"opcode",op,"owner",owner,"name",n,"descriptor",d,"interface",itf));}
 public void visitInvokeDynamicInsn(String n,String d,Handle h,Object...args){calls.add(m("instruction",ordinal++,"line",line,"opcode",186,"name",n,"descriptor",d,"bootstrap",h.toString(),"bootstrap_arguments",Arrays.stream(args).map(Object::toString).toList()));}
 public void visitFieldInsn(int op,String owner,String n,String d){state.add(m("instruction",ordinal++,"line",line,"opcode",op,"owner",owner,"name",n,"descriptor",d));}
 public void visitJumpInsn(int op,Label target){branches.add(m("instruction",ordinal++,"opcode",op,"target",label(target)));}
 public void visitTableSwitchInsn(int min,int max,Label def,Label... ls){branches.add(m("instruction",ordinal++,"opcode",170,"min",min,"max",max,"default",label(def),"targets",Arrays.stream(ls).map(l->label(l)).toList()));}
 public void visitLookupSwitchInsn(Label def,int[] keys,Label[] ls){branches.add(m("instruction",ordinal++,"opcode",171,"keys",Arrays.stream(keys).boxed().toList(),"default",label(def),"targets",Arrays.stream(ls).map(l->label(l)).toList()));}
 public void visitTryCatchBlock(Label start,Label end,Label handler,String type){handlers.add(m("start",label(start),"end",label(end),"handler",label(handler),"type",type));}
 public void visitLocalVariable(String n,String d,String s,Label start,Label end,int index){locals.add(m("name",n,"descriptor",d,"slot",index,"start",label(start),"end",label(end)));}
 public void visitMaxs(int stack,int locals){method.put("max_stack",stack);method.put("max_locals",locals);}
 public void visitEnd(){method.put("instruction_count",ordinal);}
 };}
 },0);out.println(json(r));
 }catch(Throwable e){out.println(json(m("location",location,"error",e.toString())));}
 }out.flush();
 }
}

package pt.ist.ave.jzx.Instructions;

public class JRZ_D extends Instruction {

	public JRZ_D(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		if (m_zeroF) {
//			m_tstates += 12;
//			m_pc16 = add16(m_pc16, (byte) m_memory.read8(m_pc16) + 1);
//		} else {
//			m_tstates += 7;
//			inc16pc();
//		}
		if(_cpu.getM_zeroF()){
			_cpu.setM_pc16(_cpu.add16(_cpu.getM_pc16(),(byte)_cpu.getM_memory().read8(_cpu.getM_pc16()) + 1));
		}else{
			_cpu.inc16pc();
		}
	}

	@Override
	public int incTstates() {
		if(_cpu.getM_zeroF()){
			return 12;
		}else{
			return 7;
		}
	}

}

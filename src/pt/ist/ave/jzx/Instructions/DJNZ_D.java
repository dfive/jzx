package pt.ist.ave.jzx.Instructions;

public class DJNZ_D extends Instruction {

	private int _tstates;
	
	public DJNZ_D(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
		int m_b8 = _cpu.getM_b8();
		int m_pc16 = _cpu.getM_pc16();
		
		if (m_b8 != 0) {
			_tstates = 13;
			_cpu.setM_pc16(_cpu.add16(m_pc16, (byte) _cpu.getM_memory().read8(m_pc16) + 1));
		} else {
			_tstates = 8;
			_cpu.inc16pc();
		}
	}

	@Override
	public int incTstates() {
		return _tstates;
	}

}
